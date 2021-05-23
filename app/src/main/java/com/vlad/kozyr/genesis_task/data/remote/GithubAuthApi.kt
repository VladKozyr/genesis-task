package com.vlad.kozyr.genesis_task.data.remote

import com.vlad.kozyr.genesis_task.data.remote.model.AuthResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface GithubAuthApi {

    @Headers(
        "Accept: application/json",
        "User-Agent: Genesis-App"
    )
    @POST("login/oauth/access_token")
    fun auth(
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("code") code: String
    ): Single<AuthResponse>
}