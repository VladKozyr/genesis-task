package com.vlad.kozyr.genesis_task.data.local.db

import androidx.room.*
import com.google.android.material.circularreveal.CircularRevealHelper
import com.vlad.kozyr.genesis_task.data.local.entity.RepoEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

@Dao
interface RepoDao {

    @Insert
    fun insert(repo: RepoEntity): Maybe<Long>

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun update(repo: RepoEntity): Single<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(repo: RepoEntity): Single<Long>

    @Query("SELECT * FROM repos ORDER BY timestamp DESC LIMIT :limit")
    fun getLatestRepos(limit: Int): Single<List<RepoEntity>>

    @Query("SELECT count(*) FROM repos WHERE id LIKE :id")
    fun isExistInHistory(id: Int): Single<Boolean>
}