package com.vlad.kozyr.genesis_task.data.remote.model

import com.google.gson.annotations.SerializedName


data class RepoResponse(
    @SerializedName("created_at")
    val created_at: String,
    @SerializedName("description")
    val description: String? = "",
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("html_url")
    val htmlUrl: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("node_id")
    val nodeId: String,
    @SerializedName("pushed_at")
    val pushedAt: String,
    @SerializedName("stargazers_count")
    val stargazersCount: Int,
    @SerializedName("score")
    val score: Double,
    @SerializedName("size")
    val size: Int,
    @SerializedName("updated_at")
    val updatedAt: String
)