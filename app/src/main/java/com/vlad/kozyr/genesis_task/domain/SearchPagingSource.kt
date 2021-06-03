package com.vlad.kozyr.genesis_task.domain

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.vlad.kozyr.genesis_task.core.main.errorMap
import com.vlad.kozyr.genesis_task.data.remote.GithubSearchApi
import com.vlad.kozyr.genesis_task.data.remote.model.Order
import com.vlad.kozyr.genesis_task.data.remote.model.RepoResponse
import com.vlad.kozyr.genesis_task.data.remote.model.SearchResponse
import com.vlad.kozyr.genesis_task.data.remote.model.Sort
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class SearchPagingSource(
    private val searchApi: GithubSearchApi,
    private val query: String
) : RxPagingSource<Int, RepoResponse>() {
    override fun getRefreshKey(state: PagingState<Int, RepoResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, RepoResponse>> {
        if (query.isEmpty()) {
            return Single.just(LoadResult.Page(emptyList(), prevKey = null, nextKey = null))
        }
        val page = params.key ?: 1
        val pageSize = params.loadSize

        return Single.create { emitter ->
            Single.zip(
                makeRequest(page = page * 2 - 1, pageSize = pageSize / 2),
                makeRequest(page = page * 2, pageSize = pageSize / 2),
                { first, second -> first + second }
            )
                .errorMap()
                .subscribe({
                    emitter.onSuccess(
                        LoadResult.Page(
                            data = it.repoResponses,
                            prevKey = if (page == 1) null else page - 1,
                            nextKey = if (it.totalCount / pageSize <= page) null else page + 1
                        )
                    )
                }, { emitter.onSuccess(LoadResult.Error(it)) })
        }
    }

    private fun makeRequest(page: Int, pageSize: Int): Single<SearchResponse> {
        return searchApi.search(
            query = query,
            page = page,
            perPage = pageSize,
            sort = Sort.STAR,
            order = Order.DSC
        )
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
    }
}