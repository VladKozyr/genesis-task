package com.vlad.kozyr.genesis_task.core.network

interface TokenHandler {
    fun getToken(): String
    fun refreshToken(token: String)
}