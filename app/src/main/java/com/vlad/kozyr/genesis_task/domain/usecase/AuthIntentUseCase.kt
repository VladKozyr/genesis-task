package com.vlad.kozyr.genesis_task.domain.usecase

import android.content.Intent
import com.vlad.kozyr.genesis_task.domain.repo.AuthRepository
import javax.inject.Inject

class AuthIntentUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    fun execute(username: String): Intent {
        return authRepository.authIntent(username)
    }
}