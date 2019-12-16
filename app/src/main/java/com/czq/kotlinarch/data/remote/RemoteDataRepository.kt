package com.czq.kotlinarch.data.remote

import com.czq.kotlinarch.BuildConfig
import com.czq.kotlinarch.data.model.*
import com.czq.kotlinarch.data.remote.api.RemoteApi
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.fastjson.FastJsonConverterFactory
import java.util.concurrent.TimeUnit


class RemoteDataRepository {
    private val mMockDataRepository = MockDataRepository()

    private val okHttpClient: OkHttpClient by lazy {

        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            // development build
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            // production build
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        }

        val client = OkHttpClient.Builder()
                .retryOnConnectionFailure(true)//连接失败后是否重新连接
                .connectTimeout(5, TimeUnit.SECONDS)//超时时间15S
                .addInterceptor(logging)
                .build()
        client
    }
    private val retrofit by lazy {
        Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://47.98.129.57:8080/info-admin-web/")
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    private val remoteApi: RemoteApi by lazy { retrofit.create(RemoteApi::class.java!!) }


    fun getChallengeRecommond(pageNo: Int, pageSize: Int): Observable<Page<ChallengeRecomand>> {
        return mMockDataRepository.getChallengeRecommond()
    }

    fun getUser(): Observable<User> {
        return remoteApi.getUser("manondidi", "12345566").map { getData(it) }
//        return mMockDataRepository.getUser()
    }


    fun getGames(pageNum: Int, pageSize: Int): Observable<Page<Game>> {
        return remoteApi.getGames(pageNum, pageSize).map { getData(it) }
    }

    fun getFeedArticles(pageSize: Int, offsetId: String?, direction: String): Observable<List<FeedArticle>> {
        return remoteApi.getArticleFeeds(pageSize, offsetId, direction).map { getData(it) }
    }

    fun getBanners(): Observable<List<Banner>> {
        return mMockDataRepository.getBanners()
    }

    fun <T> getData(result: Result<T>): T? {
        if (result.status != 0) {
            throw RuntimeException(result.msg)
        }
        return result.data
    }

}