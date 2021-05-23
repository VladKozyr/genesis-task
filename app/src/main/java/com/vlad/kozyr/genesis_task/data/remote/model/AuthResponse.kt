package com.vlad.kozyr.genesis_task.data.remote.model

data class AuthResponse(
    val access_token: String,
    val scope: String,
    val token_type: String
)