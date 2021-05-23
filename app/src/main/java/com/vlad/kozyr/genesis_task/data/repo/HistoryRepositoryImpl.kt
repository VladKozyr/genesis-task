package com.vlad.kozyr.genesis_task.data.repo

import com.vlad.kozyr.genesis_task.data.local.db.RepoDao
import com.vlad.kozyr.genesis_task.data.local.entity.RepoEntity
import com.vlad.kozyr.genesis_task.domain.repo.HistoryRepository
import dagger.hilt.android.scopes.ActivityScoped
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val repoDao: RepoDao
) : HistoryRepository {
    override fun observeHistory(): Single<List<RepoEntity>> {
        return repoDao.getLatestRepos(limit = 30)
    }

    override fun upsertRepo(repo: RepoEntity): Single<Long> {
        return repoDao.upsert(repo)
    }

    override fun isExistInHistory(id: Int): Single<Boolean> {
        return repoDao.isExistInHistory(id)
    }
}