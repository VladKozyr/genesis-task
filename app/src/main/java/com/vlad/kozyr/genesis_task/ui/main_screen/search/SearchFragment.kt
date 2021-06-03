package com.vlad.kozyr.genesis_task.ui.main_screen.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.vlad.kozyr.genesis_task.core.network.UnauthorizedError
import com.vlad.kozyr.genesis_task.databinding.SearchFragmentBinding
import com.vlad.kozyr.genesis_task.ui.main_screen.BaseRepoFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class SearchFragment : BaseRepoFragment() {
    private val viewModel: SearchViewModel by viewModels()

    private val repoAdapter: SearchRepoAdapter by lazy { SearchRepoAdapter(listener) }
    private lateinit var binding: SearchFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initializeRecyclerView()

        viewModel.apply {
            init()
            results.observe(viewLifecycleOwner, {
                repoAdapter.submitData(lifecycle, it)
            })

            isValid.observe(viewLifecycleOwner, {
                binding.searchBtn.isEnabled = it
            })
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.searchQueryChanged(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
        })

        binding.searchBtn.setOnClickListener {
            viewModel.processSearchQuery()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun initializeRecyclerView() {
        binding.recyclerView.apply {
            adapter = repoAdapter.withLoadStateHeaderAndFooter(
                header = SearchLoadStateAdapter { repoAdapter.retry() },
                footer = SearchLoadStateAdapter { repoAdapter.retry() }
            )
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        repoAdapter.addLoadStateListener { state ->
            if (state.refresh is LoadState.Error) {
                processException((state.refresh as LoadState.Error).error)
            }

            val isUpdating = state.refresh == LoadState.Loading
            binding.apply {
                loading.isVisible = isUpdating
                emptySearchGroup.isVisible = repoAdapter.itemCount == 0 && !isUpdating
            }
        }
    }

    private fun processException(exception: Throwable) {
        Toast.makeText(requireContext(), exception.message, Toast.LENGTH_SHORT).show()
        when (exception) {
            is UnauthorizedError -> {
                logoutListener?.onLogout()
            }
            is InternalError -> throw Exception()
            else -> exception.message?.let { m -> Log.i("NETWORK", m) }
        }
    }
}