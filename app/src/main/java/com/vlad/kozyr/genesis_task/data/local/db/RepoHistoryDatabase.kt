package com.vlad.kozyr.genesis_task.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vlad.kozyr.genesis_task.data.local.entity.RepoEntity

@Database(entities = [RepoEntity::class], version = 1, exportSchema = false)
abstract class RepoHistoryDatabase : RoomDatabase() {
    abstract val repoDao: RepoDao
}