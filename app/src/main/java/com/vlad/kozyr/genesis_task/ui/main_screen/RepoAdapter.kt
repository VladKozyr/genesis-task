package com.vlad.kozyr.genesis_task.ui.main_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vlad.kozyr.genesis_task.databinding.RepoListItemBinding

class RepoAdapter(private val listener: OnRepoClickListener?) :
    ListAdapter<RepoView, RepoAdapter.RepoViewHolder>(RepoDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RepoViewHolder(RepoListItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = getItem(position)
        return holder.bind(repo)
    }

    inner class RepoViewHolder(private val binding: RepoListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(repo: RepoView) {
            binding.repo = repo
            binding.callback = listener
        }
    }
}

