package com.vlad.kozyr.genesis_task.core.di

import com.vlad.kozyr.genesis_task.core.SessionManager
import com.vlad.kozyr.genesis_task.core.network.AuthInterceptor
import com.vlad.kozyr.genesis_task.core.network.OAuthAuthenticator
import com.vlad.kozyr.genesis_task.core.network.TokenHandler
import com.vlad.kozyr.genesis_task.core.network.TokenHandlerImpl
import com.vlad.kozyr.genesis_task.domain.repo.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideTokenHandler(sessionManager: SessionManager): TokenHandler {
        return TokenHandlerImpl(sessionManager)
    }

    @Provides
    @Singleton
    fun provideAuthenticator(
        tokenHandler: TokenHandler
    ): Authenticator {
        return OAuthAuthenticator(
            tokenHandler = tokenHandler
        )
    }

    @Provides
    fun provideAuthInterceptor(tokenHandler: TokenHandler): AuthInterceptor {
        return AuthInterceptor(tokenHandler)
    }

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BASIC) }
    }

    @Provides
    @AuthInterceptorOkHttpClient
    fun provideAuthHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor,
    ): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @OtherInterceptorOkHttpClient
    fun provideHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @AuthRetrofit
    fun provideRetrofitClient(@OtherInterceptorOkHttpClient httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://github.com")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    @Provides
    @ApiRetrofit
    fun provideApiRetrofitClient(@AuthInterceptorOkHttpClient httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }
}