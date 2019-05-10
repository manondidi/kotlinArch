package com.czq.kotlinarch.example


import android.annotation.SuppressLint
import android.widget.Toast
import com.czq.kotlin_arch.basePage.base.BasePagingPrensenterImpl
import com.czq.kotlin_arch.paging.PagingStrategy
import com.czq.kotlin_arch.paging.offset.OffsetPageInfo
import com.czq.kotlin_arch.paging.offset.OffsetStrategy
import com.czq.kotlinarch.data.model.FeedArticle
import com.czq.kotlinarch.data.remote.RemoteDataRepository
import com.czq.kotlinarch.data.viewModel.BannerList
import com.uber.autodispose.lifecycle.autoDisposable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import com.hwangjr.rxbus.thread.EventThread
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag


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

        if (pageInfo.isFirstPage()) {
            Observable.zip(getBannerList(),
                    getFeedArticle(pageInfo.pageSize, pageInfo.offsetId, pageInfo.type),
                    BiFunction<BannerList, List<Any>, Map<String, Any?>>
                    { bannerList, feedArticles ->
                        val map = mutableMapOf<String, Any?>()
                        map.put("feedArticles", feedArticles)
                        map.put("bannerList", bannerList)
                        map
                    }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .autoDisposable(mView.autoDispose())
                    .subscribe({ it ->
                        val feedArticles = it.get("feedArticles") as? List<Any>
                        val bannerList = it.get("bannerList") as? BannerList
                        datasource.clear()
                        pagingList.clear()
                        if (bannerList != null) {
                            datasource.add(bannerList)
                        }
                        pagingList.addAll(feedArticles ?: arrayListOf())//用于计算分页的数据
                        datasource.addAll(feedArticles ?: arrayListOf())
                        loadSuccess(it)
                    }, {
                        loadFail(it)
                    })

        } else {

            getFeedArticle(pageInfo.pageSize, pageInfo.offsetId, pageInfo.type)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .autoDisposable(mView.autoDispose())
                    .subscribe({ it ->
                        pagingList.addAll(it ?: arrayListOf())//用于计算分页的数据
                        datasource.addAll(it ?: arrayListOf())
                        loadSuccess(it)
                    }, {
                        loadFail(it)
                    })
        }


    }

    fun getBannerList(): Observable<BannerList> {
        return mRemoteDataRepository.getBanners().map {
            val bannerList = BannerList()
            bannerList.banners = it
            bannerList
        }.onErrorReturn { null }
    }

    fun getFeedArticle(pageSize: Int, offsetId: String?, direction: String): Observable<List<FeedArticle>> {
        return mRemoteDataRepository.getFeedArticles(pageSize, offsetId, direction)
    }

    @Subscribe(thread = EventThread.MAIN_THREAD, tags = [Tag("hi")])
    fun hi(str:String) {
        Toast.makeText(mView.getContext(),"hi",Toast.LENGTH_LONG).show()
    }
}