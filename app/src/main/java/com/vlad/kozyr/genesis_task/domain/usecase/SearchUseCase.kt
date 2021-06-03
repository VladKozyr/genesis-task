package com.vlad.kozyr.genesis_task.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.vlad.kozyr.genesis_task.core.main.Mapper
import com.vlad.kozyr.genesis_task.data.remote.model.RepoResponse
import com.vlad.kozyr.genesis_task.domain.repo.HistoryRepository
import com.vlad.kozyr.genesis_task.domain.repo.SearchRepository
import com.vlad.kozyr.genesis_task.ui.main_screen.RepoView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val searchRepository: SearchRepository,
    private val historyRepository: HistoryRepository,
    private val mapper: Mapper<RepoView, RepoResponse>
) {
    fun execute(query: String): Flowable<PagingData<RepoView>> {
        return searchRepository.search(query)
            .map { repo ->
                repo.map {
                    val repoView = mapper.toModel(it)
                    historyRepository.isExistInHistory(repoView.id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { data -> repoView.setVisited(data) }
                    repoView
                }
            }
    }
}