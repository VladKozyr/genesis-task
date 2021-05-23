package com.vlad.kozyr.genesis_task.domain.repo

import com.vlad.kozyr.genesis_task.data.local.entity.RepoEntity
import io.reactivex.rxjava3.core.Single

interface HistoryRepository {
    fun observeHistory(): Single<List<RepoEntity>>
    fun upsertRepo(repo: RepoEntity): Single<Long>
    fun isExistInHistory(id: Int): Single<Boolean>
}