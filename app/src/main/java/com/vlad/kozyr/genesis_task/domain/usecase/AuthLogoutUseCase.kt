package com.vlad.kozyr.genesis_task.domain.usecase

import com.vlad.kozyr.genesis_task.domain.repo.AuthRepository
import javax.inject.Inject

class AuthLogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
){
    fun execute() {
        authRepository.signOut()
    }
}