package com.vlad.kozyr.genesis_task.domain.repo

import android.content.Intent
import android.net.Uri
import com.vlad.kozyr.genesis_task.data.remote.model.AuthResponse
import io.reactivex.rxjava3.core.Single

interface AuthRepository {
    fun auth(uri: Uri): Single<AuthResponse>
    fun authIntent(username: String): Intent
    fun signOut(): Single<Boolean>
    fun isLoggedIn(): Boolean
}