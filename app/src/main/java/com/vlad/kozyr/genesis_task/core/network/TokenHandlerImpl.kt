package com.vlad.kozyr.genesis_task.core.network

import com.vlad.kozyr.genesis_task.core.SessionManager

class TokenHandlerImpl constructor(
    private val sessionManager: SessionManager
) : TokenHandler {

    private var token: String? = null

    override fun getToken(): String {
        token = sessionManager.fetchToken()
        return token ?: ""
    }

    override fun refreshToken(token: String) {
        sessionManager.saveToken(token)
        this.token = token
    }
}