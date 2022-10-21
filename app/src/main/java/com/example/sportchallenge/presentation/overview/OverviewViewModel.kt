package com.example.sportchallenge.presentation.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportchallenge.data.entity.Challenge
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject() constructor() : ViewModel() {
    private val testChallenge = Challenge(1, "New Challenge",
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor.",
        "1. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor.")
    private val _challengeState = MutableStateFlow<Challenge>(testChallenge)
    val challengeState: StateFlow<Challenge> get() = _challengeState.asStateFlow()

    fun showChallenge() {
        viewModelScope.launch {
            // add endpoint with challenge object result
            _challengeState.value = testChallenge
        }
    }

}