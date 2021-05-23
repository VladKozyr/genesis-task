package com.vlad.kozyr.genesis_task.data.remote.model

enum class Order(val order: String) {

    ASC("asc"), DSC("desc");

    override fun toString(): String {
        return name.lowercase()
    }
}