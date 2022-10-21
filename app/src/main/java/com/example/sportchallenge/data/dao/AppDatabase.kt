package com.example.sportchallenge.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Workout::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun workoutsDAO(): WorkoutsDAO

//    abstract fun connectedAppDAO(): ConnectedAppDAO
}