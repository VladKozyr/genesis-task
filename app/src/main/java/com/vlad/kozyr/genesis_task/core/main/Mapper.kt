package com.vlad.kozyr.genesis_task.core.main

interface Mapper<MODEL, ENTITY> {
    fun toModel(entity: ENTITY): MODEL
    fun fromModel(model: MODEL): ENTITY
}