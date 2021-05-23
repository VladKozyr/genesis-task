package com.vlad.kozyr.genesis_task.core.di

import com.vlad.kozyr.genesis_task.core.SessionManager
import com.vlad.kozyr.genesis_task.data.remote.GithubAuthApi
import com.vlad.kozyr.genesis_task.data.repo.AuthRepositoryImpl
import com.vlad.kozyr.genesis_task.domain.repo.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object AuthService {

    @Provides
    fun provideGithubApi(@AuthRetrofit retrofit: Retrofit): GithubAuthApi {
        return retrofit.create(GithubAuthApi::class.java)
    }

    @Provides
    fun provideAuthRepository(
        authApi: GithubAuthApi,
        sessionManager: SessionManager
    ): AuthRepository {
        return AuthRepositoryImpl(
            authApi = authApi,
            sessionManager = sessionManager
        )
    }
}