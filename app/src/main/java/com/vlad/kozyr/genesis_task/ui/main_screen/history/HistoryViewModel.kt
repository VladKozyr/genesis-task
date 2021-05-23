package com.vlad.kozyr.genesis_task.ui.main_screen.history

import android.util.Log
import androidx.lifecycle.ViewModel
import com.vlad.kozyr.genesis_task.data.local.entity.RepoEntity
import com.vlad.kozyr.genesis_task.domain.repo.HistoryRepository
import com.vlad.kozyr.genesis_task.domain.usecase.ObserveHistoryUseCase
import com.vlad.kozyr.genesis_task.domain.usecase.UpdateHistoryUseCase
import com.vlad.kozyr.genesis_task.ui.main_screen.RepoView
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val observeHistoryUseCase: ObserveHistoryUseCase,
    private val updateHistoryUseCase: UpdateHistoryUseCase
) : ViewModel() {
    private val _history = observeHistoryUseCase.execute()
    val history: Single<List<RepoView>>
        get() = _history

    fun addToHistory(repoView: RepoView) {
        updateHistoryUseCase.execute(repoView)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { data -> repoView.setVisited(data) },
                { error -> Log.i("TAG", "addToHistory: $error") })
    }
}