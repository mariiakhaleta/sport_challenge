package com.example.sportchallenge.presentation.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportchallenge.data.dao.ConnectedApp
import com.example.sportchallenge.presentation.BaseFragment
import com.example.sportchallenge.R
import com.example.sportchallenge.databinding.SettingsFragmentLayoutBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment: BaseFragment<SettingsFragmentLayoutBinding>() {

    private val viewModel: SettingsViewModel by viewModels()
    private val adapter = ConnectedAppsAdapter(this::onItemClicked)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPostsRecyclerView()

        if (arguments?.getString("code") != null) {
            val code = requireArguments().getString("code")
            Log.d("CODE SETTINGS ", code!!)
            viewModel.collectDataFromStrava(code)
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.collectionActivityStravaState.collect(this@SettingsFragment::collectDataFromStrava)
                }
            }
        }

        viewModel.showConnectedAppsList()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.connectedAppsListState.collect { showList(it) }
            }
        }
    }

    private fun collectDataFromStrava(state: Boolean) {

    }

    private fun showList(list: List<ConnectedApp>) {
        if (list.isEmpty()) {
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
        return R.layout.settings_fragment_layout
    }

    private fun onItemClicked(connectedApp: ConnectedApp) {
        if (connectedApp.name == "Strava") {
            openStravaConnection()
        }
    }

    private fun openStravaConnection() {
        val intentUri = Uri.parse("https://www.strava.com/oauth/mobile/authorize")
            .buildUpon()
            .appendQueryParameter("client_id", "93433")
            .appendQueryParameter("redirect_uri", "http://localhost")
            .appendQueryParameter("response_type", "code")
            .appendQueryParameter("scope", "activity:read_all")
            .build()

        val intent = Intent(Intent.ACTION_VIEW, intentUri)
        startActivity(intent)
    }
}