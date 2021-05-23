package com.vlad.kozyr.genesis_task.domain.repo

import androidx.paging.PagingData
import com.vlad.kozyr.genesis_task.data.remote.model.RepoResponse
import com.vlad.kozyr.genesis_task.data.remote.model.SearchResponse
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

interface SearchRepository {
    fun search(query: String): Flowable<PagingData<RepoResponse>>
}