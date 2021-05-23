package com.vlad.kozyr.genesis_task.data.remote.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("incomplete_results")
    val incomplete_results: Boolean,
    @SerializedName("items")
    val repoResponses: List<RepoResponse>,
    @SerializedName("total_count")
    val totalCount: Int
) {
    operator fun plus(second: SearchResponse): SearchResponse {
        return SearchResponse(
            incomplete_results = incomplete_results && second.incomplete_results,
            repoResponses = repoResponses + second.repoResponses,
            totalCount = totalCount + second.totalCount
        )
    }
}