package com.czq.kotlinarch.data.remote

import com.czq.kotlinarch.data.model.ChallengeRecomand
import com.czq.kotlinarch.data.model.Page
import com.czq.kotlinarch.data.model.Result
import com.czq.kotlinarch.data.model.User
import com.czq.kotlinarch.data.remote.api.RemoteApi
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.fastjson.FastJsonConverterFactory
import java.util.concurrent.TimeUnit


class RemoteDataRepository {
    val mMockDataRepository = MockDataRepository()

    val okHttpClient by lazy {
        val client = OkHttpClient.Builder()
            .retryOnConnectionFailure(true)//连接失败后是否重新连接
            .connectTimeout(5, TimeUnit.SECONDS)//超时时间15S
            .build()
        client
    }
    val retrofit by lazy {
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("http://47.98.129.57:8080/info-admin-web/")
            .addConverterFactory(FastJsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    val remoteApi: RemoteApi by lazy { retrofit.create(RemoteApi::class.java!!) }


    fun getChallengeRecommond(pageNo: Int, pageSize: Int): Observable<Page<ChallengeRecomand>> {
        return mMockDataRepository.getChallengeRecommond()
    }

    fun getUser(): Observable<User> {
        return remoteApi.getUser("manondidi", "12345566").map { getData(it) }
//        return mMockDataRepository.getUser()
    }

    fun <T> getData(result: Result<T>): T {
        if (result.status != 0) {
            throw RuntimeException(result.msg)
        }
        return result.data
    }

}