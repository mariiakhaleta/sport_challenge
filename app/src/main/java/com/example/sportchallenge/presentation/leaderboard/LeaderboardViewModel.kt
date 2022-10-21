package com.example.sportchallenge.presentation.leaderboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportchallenge.data.entity.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeaderboardViewModel @Inject() constructor() : ViewModel() {
    val testListUsers = listOf(
        User(1, "User1", "1", "Yoga"),
        User(2, "User2", "2", "Jogging"),
        User(2, "User3", "3", "Football")
    )
    private val _usersListState = MutableStateFlow<List<User>>(emptyList())
    val usersListState: StateFlow<List<User>> get() = _usersListState.asStateFlow()

    fun showLeaderboardList() {
        viewModelScope.launch {
            // add endpoint with users leaderboard object result
            _usersListState.value = testListUsers
        }
    }
}