package com.vlad.kozyr.genesis_task.ui.main_screen.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.vlad.kozyr.genesis_task.core.main.DisposableViewModel
import com.vlad.kozyr.genesis_task.domain.usecase.SearchUseCase
import com.vlad.kozyr.genesis_task.ui.main_screen.RepoView
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.processors.PublishProcessor
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
) : DisposableViewModel() {

    private val searchProcessor: PublishProcessor<String> = PublishProcessor.create()

    private val _queryData = MutableLiveData<String>()

    private val _results: MutableLiveData<PagingData<RepoView>> = MutableLiveData()
    val results: LiveData<PagingData<RepoView>>
        get() = _results

    private val _isValid = MutableLiveData(false)
    val isValid: LiveData<Boolean> = _isValid

    @ExperimentalCoroutinesApi
    fun init() {
        searchProcessor.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { searchUseCase.execute(query = it).cachedIn(viewModelScope) }
            .subscribe { value -> _results.postValue(value) }
            .autoClear()
    }

    fun processSearchQuery() {
        searchProcessor.onNext(_queryData.value)
    }

    fun searchQueryChanged(query: String) {
        _isValid.postValue(query.isNotBlank())
        _queryData.postValue(query)
    }
}