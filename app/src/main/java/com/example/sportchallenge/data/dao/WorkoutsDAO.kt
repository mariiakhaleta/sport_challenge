package com.example.sportchallenge.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
abstract class WorkoutsDAO {

    @Query("DELETE FROM workouts_list")
    abstract fun deleteAllData()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertWorkouts(repositoriesList: List<Workout>)

    @Query("SELECT * FROM workouts_list ORDER BY date DESC")
    abstract fun loadWorkoutsList(): Flow<List<Workout>>
}