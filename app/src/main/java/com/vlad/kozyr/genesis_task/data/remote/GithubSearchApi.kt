package com.vlad.kozyr.genesis_task.data.remote

import com.vlad.kozyr.genesis_task.data.remote.model.SearchResponse
import com.vlad.kozyr.genesis_task.data.remote.model.Sort
import com.vlad.kozyr.genesis_task.data.remote.model.Order
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GithubSearchApi {

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("search/repositories")
    fun search(
        @Query("q") query: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
        @Query("sort") sort: Sort,
        @Query("order") order: Order
    ): Single<SearchResponse>
}