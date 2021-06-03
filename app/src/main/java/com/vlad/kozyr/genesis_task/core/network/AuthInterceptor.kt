package com.vlad.kozyr.genesis_task.core.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor constructor(
    private val tokenHandler: TokenHandler
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().signedRequest()
        return chain.proceed(newRequest)
    }

    private fun Request.signedRequest(): Request {
        val accessToken = tokenHandler.getToken()
        Log.i("TAG", "signedRequest: $accessToken")
        return newBuilder()
            .header("Authorization", "token $accessToken")
            .build()
    }
}