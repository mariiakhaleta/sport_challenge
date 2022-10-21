package com.example.sportchallenge.presentation.leaderboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportchallenge.presentation.BaseFragment
import com.example.sportchallenge.R
import com.example.sportchallenge.data.entity.User
import com.example.sportchallenge.databinding.LeaderboardFragmentLayoutBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LeaderboardFragment: BaseFragment<LeaderboardFragmentLayoutBinding>() {
    private val viewModel: LeaderboardViewModel by viewModels()
    private val adapter = LeaderboardAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPostsRecyclerView()

        viewModel.showLeaderboardList()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.usersListState.collect { showList(it) }
            }
        }
    }

    private fun showList(list: List<User>) {
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
        return R.layout.leaderboard_fragment_layout
    }
}