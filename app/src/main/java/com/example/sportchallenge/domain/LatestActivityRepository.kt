package com.example.sportchallenge.domain

import com.example.sportchallenge.data.dao.Workout
import com.example.sportchallenge.data.dao.WorkoutsDAO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LatestActivityRepository @Inject constructor(
    private val workoutsDAO: WorkoutsDAO,
) {
    fun showWorkouts(): Flow<List<Workout>> {
        return workoutsDAO.loadWorkoutsList()
    }
}