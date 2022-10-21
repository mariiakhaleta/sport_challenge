package com.example.sportchallenge.presentation.latestactivity

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportchallenge.data.dao.Workout
import com.example.sportchallenge.R
import com.example.sportchallenge.databinding.LatestActivityLayoutBinding
import com.example.sportchallenge.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LatestActivityFragment : BaseFragment<LatestActivityLayoutBinding>() {
    private val viewModel: LatestActivityViewModel by viewModels()
    private val adapter = LatestActivityAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPostsRecyclerView()

        viewModel.showLatestActivityList()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.latestActivityListState.collect { showList(it) }
            }
        }
    }

    private fun showList(list: List<Workout>) {
        if (list.isNullOrEmpty()) {
            // list empty
        } else {
            adapter.submitList(list)
        }
    }

    private fun initPostsRecyclerView() {
        binding?.apply {
            recyclerview.adapter = adapter
            recyclerview.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL, false
            )
        }
    }

    override fun layoutId(): Int {
        return R.layout.latest_activity_layout
    }
}