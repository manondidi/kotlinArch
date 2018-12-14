package com.czq.kotlinarch.data.remote

import android.content.Context
import com.alibaba.fastjson.TypeReference
import com.czq.kotlin_arch.common.util.MockUtil
import com.czq.kotlinarch.App
import com.czq.kotlinarch.data.model.ChallengeRecomand
import com.czq.kotlinarch.data.model.Page
import io.reactivex.Observable

class MockDataRepository() {

    fun getChallengeRecommond(): Observable<Page<ChallengeRecomand>> {
        return Observable.just(MockUtil.getMockModel(App.application, "challenge_recommond.json",
            object : TypeReference<Page<ChallengeRecomand>>() {}
        ))
    }

}