package com.czq.kotlinarch.data.remote

import com.czq.kotlinarch.data.model.ChallengeRecomand
import com.czq.kotlinarch.data.model.Page
import io.reactivex.Observable

class RemoteDataRepository {
    val mMockDataRepository = MockDataRepository()

    fun getChallengeRecommond(pageNo:Int,pageSize:Int): Observable<Page<ChallengeRecomand>> {
        return mMockDataRepository.getChallengeRecommond()
    }
}