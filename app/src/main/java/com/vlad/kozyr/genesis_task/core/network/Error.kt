package com.vlad.kozyr.genesis_task.core.network

sealed class Error(message: String) : Throwable(message)

open class NetworkError(val statusCode: Int = -1, message: String = "Network error") :
    Error(message)

class UnauthorizedError : NetworkError(401, "Please update session")
class InternalServerError : NetworkError(500, "Internal server error")

open class NoNetworkError : Error("Please turn on internet")

open class ServerUnreachable : Error("Server unreachable")