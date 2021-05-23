package com.vlad.kozyr.genesis_task.ui.main_screen.history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.vlad.kozyr.genesis_task.databinding.HistoryFragmentBinding
import com.vlad.kozyr.genesis_task.ui.main_screen.BaseRepoFragment
import com.vlad.kozyr.genesis_task.ui.main_screen.RepoAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

@AndroidEntryPoint
class HistoryFragment : BaseRepoFragment() {

    private val historyViewModel: HistoryViewModel by viewModels()
    private val historyAdapter: RepoAdapter by lazy { RepoAdapter(listener) }

    private lateinit var binding: HistoryFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HistoryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.historyList.apply {
            adapter = historyAdapter
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        historyViewModel.history
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { value ->
                    binding.emptyResultGroup.isVisible = value.isEmpty()
                    historyAdapter.submitList(value)
                },
                { error -> Log.i("TAG", "onCreate: $error") }
            )
    }
}