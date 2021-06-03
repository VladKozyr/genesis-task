package com.vlad.kozyr.genesis_task.core.network

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.HttpException

/*
    This class for cases where we can refresh our access token for api.
 */
class OAuthAuthenticator constructor(
    private val tokenHandler: TokenHandler,
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        return when {
            response.retryCount > RETRY_COUNT -> {
                null
            }
            else -> response.createSignedRequest()
        }
    }

    private fun Response.createSignedRequest(): Request {
        // TODO("fetch new token")
        return request.newBuilder()
            //.addHeader("Authorization", "token ${tokenHandler.getToken()}")
            .build()
    }

    private val Response.retryCount: Int
        get() {
            var currentResponse = priorResponse
            var result = 0
            while (currentResponse != null) {
                result++
                currentResponse = currentResponse.priorResponse
            }
            return result
        }

    companion object {
        private const val RETRY_COUNT = 3
    }
}
