package com.example.sportchallenge.data.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

abstract class ConnectedAppDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertConnectedApps(list: List<ConnectedApp>)

    @Query("SELECT * FROM connected_app_list")
    abstract fun loadConnectedAppList(): Flow<List<ConnectedApp>>
}