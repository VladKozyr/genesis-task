package com.vlad.kozyr.genesis_task.core.di

import android.content.Context
import com.vlad.kozyr.genesis_task.core.Mapper
import com.vlad.kozyr.genesis_task.core.SessionManager
import com.vlad.kozyr.genesis_task.data.local.db.RepoDao
import com.vlad.kozyr.genesis_task.data.local.entity.RepoEntity
import com.vlad.kozyr.genesis_task.data.remote.GithubSearchApi
import com.vlad.kozyr.genesis_task.data.remote.model.RepoResponse
import com.vlad.kozyr.genesis_task.data.repo.HistoryRepositoryImpl
import com.vlad.kozyr.genesis_task.data.repo.SearchRepositoryImpl
import com.vlad.kozyr.genesis_task.domain.mapper.RepoEntityMapper
import com.vlad.kozyr.genesis_task.domain.mapper.RepoResponseMapper
import com.vlad.kozyr.genesis_task.domain.repo.HistoryRepository
import com.vlad.kozyr.genesis_task.domain.repo.SearchRepository
import com.vlad.kozyr.genesis_task.ui.main_screen.RepoView
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    @Provides
    fun provideRepoResponseMapper(): Mapper<RepoView, RepoResponse> {
        return RepoResponseMapper()
    }

    @Provides
    fun provideRepoEntityMapper(): Mapper<RepoView, RepoEntity> {
        return RepoEntityMapper()
    }

    @Provides
    fun provideGithubSearchApi(@ApiRetrofit retrofit: Retrofit): GithubSearchApi {
        return retrofit.create(GithubSearchApi::class.java)
    }

    @Provides
    fun provideSearchRepository(
        githubSearchApi: GithubSearchApi,
    ): SearchRepository {
        return SearchRepositoryImpl(
            githubSearchApi = githubSearchApi,
        )
    }

    @Provides
    fun provideHistoryRepository(
        repoDao: RepoDao,
    ): HistoryRepository {
        return HistoryRepositoryImpl(
            repoDao = repoDao
        )
    }

    @Provides
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager {
        return SessionManager(context)
    }
}