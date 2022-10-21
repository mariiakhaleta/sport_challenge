package com.example.sportchallenge.data.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "connected_app_list")
data class ConnectedApp (
    @PrimaryKey val id: Int,
    val name: String,
    var synchronized: Boolean,
)