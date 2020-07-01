package com.czq.kotlinarch.example

import android.annotation.SuppressLint
import com.czq.kotlin_arch.basePage.base.BasePagingPrensenterImpl
import com.czq.kotlin_arch.paging.PagingStrategy
import com.czq.kotlin_arch.paging.normal.NormalPagingInfo
import com.czq.kotlin_arch.paging.normal.NormalPagingStrategy
import com.czq.kotlinarch.data.remote.RemoteDataRepository
import com.czq.kotlinarch.data.viewModel.GameDate
import com.uber.autodispose.lifecycle.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class GameListPresenter(override val mView: GameListContact.PagingListView) :
    BasePagingPrensenterImpl(mView),
    GameListContact.PagingListPresenter {
    var day = 30

    val mRemoteDataRepository: RemoteDataRepository by lazy {
        RemoteDataRepository()
    }

    override fun getPagingStrategy(): PagingStrategy? {
        return NormalPagingStrategy()
    }

    @SuppressLint("CheckResult")
    override fun onLoadData(pagingStrategy: PagingStrategy?) {
        val pageInfo = pagingStrategy!!.getPageInfo() as NormalPagingInfo
        mRemoteDataRepository.getGames(pageInfo.pageNum, pageInfo.pageSize)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(mView.autoDispose())
            .subscribe({ it ->
                if (pageInfo.isFirstPage()) {
                    datasource.clear()
                    pagingList.clear()
                    val gd = GameDate()
                    gd.date = "今日"
                    datasource.add(gd)
                } else {
                    val gd = GameDate()
                    gd.date = "2018-12-${day--}"
                    datasource.add(gd)
                }
                pagingList.addAll(it?.listData ?: arrayListOf())//用于计算分页的数据
                datasource.addAll(it?.listData ?: arrayListOf())
                loadSuccess(it)
            }, {
                loadFail(it)
            })
    }
}