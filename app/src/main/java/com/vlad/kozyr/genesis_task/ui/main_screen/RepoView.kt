package com.vlad.kozyr.genesis_task.ui.main_screen

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.vlad.kozyr.genesis_task.BR

data class RepoView(
    val id: Int,
    val name: String,
    val description: String,
    val url: String,
    val stars: Int,
) : BaseObservable() {

    private var _isVisited: Boolean = false

    @Bindable
    fun getVisited() = _isVisited

    fun setVisited(isVisited: Boolean) {
        if (isVisited != _isVisited) {
            _isVisited = isVisited
            notifyPropertyChanged(BR.visited)
        }
    }
}