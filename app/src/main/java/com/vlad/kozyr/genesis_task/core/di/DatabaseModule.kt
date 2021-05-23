package com.vlad.kozyr.genesis_task.core.di

import android.content.Context
import androidx.room.Room
import com.vlad.kozyr.genesis_task.data.local.db.RepoDao
import com.vlad.kozyr.genesis_task.data.local.db.RepoHistoryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideRepoDao(repoHistoryDatabase: RepoHistoryDatabase): RepoDao {
        return repoHistoryDatabase.repoDao
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): RepoHistoryDatabase {
        return Room.databaseBuilder(
            context,
            RepoHistoryDatabase::class.java,
            "repo_history"
        ).build()
    }
}