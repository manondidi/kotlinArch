package com.czq.kotlinarch.example


import android.annotation.SuppressLint
import com.czq.kotlin_arch.basePage.paging.BasePagingActivity
import com.czq.kotlinarch.R
import com.czq.kotlinarch.example.itembinder.BannerItembinder
import com.czq.kotlinarch.example.itembinder.FeedArticleItembinder
import me.drakeet.multitype.register


class FeedArticleActivity : BasePagingActivity<FeedArticleContract.IPresenter>(), FeedArticleContract.IView {

    @SuppressLint("CheckResult")
    override fun registItemBinder() {

        multiAdapter.register(FeedArticleItembinder())
        multiAdapter.register(BannerItembinder())
    }

    override fun createPresenter(): FeedArticleContract.IPresenter {
        return FeedArticlePresenter(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_feed_article
    }

    override fun initView() {
        super.initView()
    }


}