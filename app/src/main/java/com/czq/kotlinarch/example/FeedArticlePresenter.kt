package com.czq.kotlinarch.example


import android.annotation.SuppressLint
import com.czq.kotlin_arch.basePage.base.BasePagingPrensenterImpl
import com.czq.kotlin_arch.paging.PagingStrategy
import com.czq.kotlin_arch.paging.offset.OffsetPageInfo
import com.czq.kotlin_arch.paging.offset.OffsetStrategy
import com.czq.kotlinarch.data.remote.RemoteDataRepository
import com.uber.autodispose.lifecycle.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class FeedArticlePresenter(mView: FeedArticleContract.IView) : BasePagingPrensenterImpl(mView),
        FeedArticleContract.IPresenter {
    val mRemoteDataRepository: RemoteDataRepository by lazy {
        RemoteDataRepository()
    }

    override fun getPagingStrategy(): PagingStrategy? {
        return OffsetStrategy(10, "id")
    }

    @SuppressLint("CheckResult")
    override fun onLoadData(pagingStrategy: PagingStrategy?) {
        val pageInfo = pagingStrategy!!.getPageInfo() as OffsetPageInfo
        mRemoteDataRepository.getFeedArticles(pageInfo.pageSize, pageInfo.offsetId, pageInfo.type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(mView.autoDispose())
                .subscribe({ it ->
                    if (pageInfo.isFirstPage()) {
                        datasource.clear()
                        pagingList.clear()
                    }
                    pagingList.addAll(it ?: arrayListOf())//用于计算分页的数据
                    datasource.addAll(it ?: arrayListOf())
                    loadSuccess(it)
                }, {
                    loadFail(it)
                })

    }
}