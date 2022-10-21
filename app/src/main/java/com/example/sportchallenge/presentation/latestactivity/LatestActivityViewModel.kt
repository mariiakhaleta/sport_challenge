package com.example.sportchallenge.presentation.latestactivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportchallenge.data.dao.Workout
import com.example.sportchallenge.domain.LatestActivityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LatestActivityViewModel @Inject() constructor(private val repository: LatestActivityRepository) : ViewModel() {
    val testListLatestActivity = listOf(
        Workout(1, "Activity 1", "", "Apple Health", "1", "Yoga", 1234),
        Workout(1, "Activity 2", "", "Samsung Health", "1", "Jogging", 1234),
        Workout(1, "Activity 3", "", "Strava", "1", "Yoga", 1234),
        Workout(1, "Activity 4", "", "Garmin", "1", "Bicycle", 1234),
    )

    private val _latestActivityListState = MutableStateFlow<List<Workout>>(emptyList())
    val latestActivityListState: StateFlow<List<Workout>> get() = _latestActivityListState.asStateFlow()

    fun showLatestActivityList() {
        viewModelScope.launch {
            // add endpoint with users leaderboard object result
            repository.showWorkouts().collect {
                _latestActivityListState.value = it
            }
        }
    }
}