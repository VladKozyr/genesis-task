package com.vlad.kozyr.genesis_task.domain.usecase

import com.vlad.kozyr.genesis_task.core.Mapper
import com.vlad.kozyr.genesis_task.data.local.entity.RepoEntity
import com.vlad.kozyr.genesis_task.domain.repo.HistoryRepository
import com.vlad.kozyr.genesis_task.ui.main_screen.RepoView
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UpdateHistoryUseCase @Inject constructor(
    private val historyRepository: HistoryRepository,
    private val mapper: Mapper<RepoView, RepoEntity>
) {
    fun execute(repoView: RepoView): Single<Boolean> {
        return historyRepository
            .upsertRepo(mapper.fromModel(repoView))
            .map { it > 0 }
    }
}