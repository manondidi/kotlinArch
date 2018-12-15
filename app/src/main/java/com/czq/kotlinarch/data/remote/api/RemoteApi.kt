package com.czq.kotlinarch.data.remote.api

import com.czq.kotlinarch.data.model.Result
import com.czq.kotlinarch.data.model.User
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
    fun getUser(@Path("userId") userId: String, @Query("password") passord: String): Observable<Result<User>>

}