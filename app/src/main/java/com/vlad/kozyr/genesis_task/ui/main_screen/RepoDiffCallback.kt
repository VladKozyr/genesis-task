package com.vlad.kozyr.genesis_task.ui.main_screen

import androidx.recyclerview.widget.DiffUtil

class RepoDiffCallback : DiffUtil.ItemCallback<RepoView>() {
    override fun areItemsTheSame(oldItem: RepoView, newItem: RepoView): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: RepoView, newItem: RepoView): Boolean {
        return oldItem.id == newItem.id
    }
}