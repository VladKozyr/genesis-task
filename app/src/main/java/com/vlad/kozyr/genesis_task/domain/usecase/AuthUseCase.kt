package com.vlad.kozyr.genesis_task.domain.usecase

import android.net.Uri
import android.util.Log
import com.vlad.kozyr.genesis_task.core.network.TokenHandler
import com.vlad.kozyr.genesis_task.domain.repo.AuthRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenHandler: TokenHandler
) {
    fun execute(uri: Uri): Completable {
        return authRepository.auth(uri = uri)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMapCompletable {
                tokenHandler.refreshToken(it.access_token)
                Completable.complete()
            }
    }
}