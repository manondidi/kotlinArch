package com.czq.kotlinarch.data.remote.api

import com.czq.kotlinarch.data.model.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface RemoteApi {

    @Headers(
            "X-Requested-With: XMLHttpRequest"
    )

    @GET("user/{userId}")
    fun getUser(@Path("userId") userId: String, @Query("password") password: String): Observable<Result<User>>


    @GET("archServer/games")
    fun getGames(@Query("pageNum") pageNum: Int, @Query("pageSize") pageSize: Int): Observable<Result<Page<Game>>>

    @GET("archServer/feeds")
    fun getArticleFeeds(@Query("pageSize") pageSize: Int, @Query("offsetId") offsetId: String?, @Query("direction") direction: String)
            : Observable<Result<List<FeedArticle>>>

}