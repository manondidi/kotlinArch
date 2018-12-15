package com.czq.kotlinarch.data.remote

import com.alibaba.fastjson.TypeReference
import com.czq.kotlin_arch.common.util.MockUtil
import com.czq.kotlinarch.App
import com.czq.kotlinarch.data.model.ChallengeRecomand
import com.czq.kotlinarch.data.model.Page
import com.czq.kotlinarch.data.model.User
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class MockDataRepository() {

    fun getChallengeRecommond(): Observable<Page<ChallengeRecomand>> {
        return Observable.just(MockUtil.getMockModel(App.application, "challenge_recommond.json",
            object : TypeReference<Page<ChallengeRecomand>>() {}
        )).delay(1, TimeUnit.SECONDS)
    }

    fun getUser(): Observable<User> {
        return Observable.just(MockUtil.getMockModel(App.application, "get_user.json", User::class.java))
            .delay(1, TimeUnit.SECONDS)
    }

}