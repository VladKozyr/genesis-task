package com.vlad.kozyr.genesis_task.ui.main_screen.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vlad.kozyr.genesis_task.databinding.RepoListItemBinding
import com.vlad.kozyr.genesis_task.ui.main_screen.OnRepoClickListener
import com.vlad.kozyr.genesis_task.ui.main_screen.RepoDiffCallback
import com.vlad.kozyr.genesis_task.ui.main_screen.RepoView

class SearchRepoAdapter(private val listener: OnRepoClickListener?) :
    PagingDataAdapter<RepoView, SearchRepoAdapter.RepoViewHolder>(RepoDiffCallback()) {

    inner class RepoViewHolder(private val binding: RepoListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(repo: RepoView) {
            binding.repo = repo
            binding.callback = listener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder(
            RepoListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}