package com.vlad.kozyr.genesis_task.domain.mapper

import com.vlad.kozyr.genesis_task.core.main.Mapper
import com.vlad.kozyr.genesis_task.data.remote.model.RepoResponse
import com.vlad.kozyr.genesis_task.ui.main_screen.RepoView

class RepoResponseMapper : Mapper<RepoView, RepoResponse> {
    override fun toModel(entity: RepoResponse): RepoView {
        return with(entity) {
            RepoView(
                id = id,
                name = name,
                stars = stargazersCount,
                url = htmlUrl,
                description = description ?: ""
            )
        }
    }

    override fun fromModel(model: RepoView): RepoResponse {
        TODO("Not yet implemented")
    }
}