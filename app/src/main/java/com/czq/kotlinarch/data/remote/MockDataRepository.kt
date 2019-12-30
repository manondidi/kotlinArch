package com.czq.kotlinarch.data.remote

import com.alibaba.fastjson.TypeReference
import com.czq.kotlin_arch.common.util.MockUtil
import com.czq.kotlinarch.App
import com.czq.kotlinarch.data.model.*
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class MockDataRepository() {

    fun getChallengeRecommond(): Observable<Page<ChallengeRecomand>> {
        return Observable.just(MockUtil.getMockModel(App.application, "challenge_recommond.json",
            object : TypeReference<Page<ChallengeRecomand>>() {}
        )).delay(1, TimeUnit.SECONDS)
    }

    fun getUser(): Observable<User> {
        return Observable.just(
            MockUtil.getMockModel(
                App.application,
                "get_user.json",
                User::class.java
            )
        )
            .delay(1, TimeUnit.SECONDS)
    }

    fun getBanners(): Observable<List<Banner>> {
        return Observable.just(
            MockUtil.getMockModelList(
                App.application,
                "banner.json",
                Banner::class.java
            )
        )
            .delay(1, TimeUnit.SECONDS)
    }

    fun getGames(): Observable<Page<Game>> {
        return Observable.just(
            MockUtil.getMockModel(
                App.application,
                "game.json",
                object : TypeReference<Page<Game>>() {})
        )
            .delay(1, TimeUnit.SECONDS)
    }

    fun getFeeds(): Observable<List<FeedArticle>> {
        return Observable.just(
            MockUtil.getMockModel(
                App.application,
                "feeds.json",
                object : TypeReference<List<FeedArticle>>() {})
        )
            .delay(1, TimeUnit.SECONDS)
    }
}