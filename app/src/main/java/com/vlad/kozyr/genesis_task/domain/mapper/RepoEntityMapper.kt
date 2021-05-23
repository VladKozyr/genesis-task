package com.vlad.kozyr.genesis_task.domain.mapper

import com.vlad.kozyr.genesis_task.core.Mapper
import com.vlad.kozyr.genesis_task.data.local.entity.RepoEntity
import com.vlad.kozyr.genesis_task.ui.main_screen.RepoView

class RepoEntityMapper : Mapper<RepoView, RepoEntity> {
    override fun toModel(entity: RepoEntity): RepoView {
        return with(entity) {
            RepoView(
                id = id,
                name = name,
                url = url,
                stars = stars,
                description = description
            )
        }
    }

    override fun fromModel(model: RepoView): RepoEntity {
        return with(model) {
            RepoEntity(
                id = id,
                name = name,
                url = url,
                timestamp = System.currentTimeMillis() / 1000,
                stars = stars,
                description = description
            )
        }
    }
}