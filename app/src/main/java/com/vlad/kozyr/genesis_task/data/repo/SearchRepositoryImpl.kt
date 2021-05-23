package com.vlad.kozyr.genesis_task.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.vlad.kozyr.genesis_task.data.remote.GithubSearchApi
import com.vlad.kozyr.genesis_task.data.remote.model.RepoResponse
import com.vlad.kozyr.genesis_task.domain.SearchPagingSource
import com.vlad.kozyr.genesis_task.domain.repo.SearchRepository
import dagger.hilt.android.scopes.ActivityScoped
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val githubSearchApi: GithubSearchApi
) : SearchRepository {

    @ExperimentalCoroutinesApi
    override fun search(query: String): Flowable<PagingData<RepoResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                initialLoadSize = 60
            ),
            pagingSourceFactory = { SearchPagingSource(githubSearchApi, query) }
        ).flowable
    }
}