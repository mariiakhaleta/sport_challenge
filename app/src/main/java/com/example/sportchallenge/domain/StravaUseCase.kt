package com.example.sportchallenge.domain

import kotlinx.coroutines.flow.Flow

interface StravaUseCase {
    suspend fun connectStrava(code: String): Flow<Boolean>
}