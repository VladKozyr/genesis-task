package com.vlad.kozyr.genesis_task.data.repo

import android.content.Intent
import android.net.Uri
import android.util.Log
import com.vlad.kozyr.genesis_task.BuildConfig
import com.vlad.kozyr.genesis_task.core.SessionManager
import com.vlad.kozyr.genesis_task.data.model.LoggedInUser
import com.vlad.kozyr.genesis_task.data.remote.GithubAuthApi
import com.vlad.kozyr.genesis_task.data.remote.model.AuthResponse
import com.vlad.kozyr.genesis_task.domain.repo.AuthRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val sessionManager: SessionManager,
    private val authApi: GithubAuthApi
) : AuthRepository {

    var user: LoggedInUser? = null
        private set

    init {
        user = if (sessionManager.isActive) LoggedInUser("") else null
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
    }

    override fun auth(uri: Uri): Single<AuthResponse> {
        val code = uri.getQueryParameter(QUERY_PARAM)
            ?: return Single.error(IllegalArgumentException("Incorrect param"))

        return authApi.auth(
            clientId = BuildConfig.CLIENT_ID,
            clientSecret = BuildConfig.CLIENT_SECRET,
            code = code
        ).apply {
            subscribe { _ ->
                setLoggedInUser(LoggedInUser(""))
                sessionManager.saveUser()
            }
        }
    }

    override fun authIntent(username: String): Intent {
        return Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://github.com/login/oauth/authorize?client_id=${BuildConfig.CLIENT_ID}&login=${username}")
        )
    }

    override fun signOut(): Single<Boolean> {
        user = null
        sessionManager.deleteUser()
        return Single.just(true)
    }

    override fun isLoggedIn(): Boolean {
        return user != null
    }

    companion object {
        const val QUERY_PARAM = "code"
    }
}