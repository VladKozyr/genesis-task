package com.vlad.kozyr.genesis_task.data.remote.model

enum class Sort(val sort: String) {

    STAR("stars"),
    FORK("forks"),
    HELP_WANTED_ISSUES("help-wanted-issues"),
    UPDATED("updated");

    override fun toString(): String {
        return sort
    }

}