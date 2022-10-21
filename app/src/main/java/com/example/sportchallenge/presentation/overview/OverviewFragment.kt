package com.example.sportchallenge.presentation.overview

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.sportchallenge.data.entity.Challenge
import com.example.sportchallenge.presentation.BaseFragment
import com.example.sportchallenge.R
import com.example.sportchallenge.databinding.OverviewFragmentLayoutBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OverviewFragment: BaseFragment<OverviewFragmentLayoutBinding>() {

    private val viewModel: OverviewViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.showChallenge()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.challengeState.collect { showChallenge(it) }
            }
        }
    }

    private fun showChallenge(challenge: Challenge) {
        binding?.challengeName?.text = challenge.name
        binding?.challengeDesc?.text = challenge.description
        binding?.challengeRules?.text = challenge.rules
    }

    override fun layoutId(): Int {
        return R.layout.overview_fragment_layout
    }
}