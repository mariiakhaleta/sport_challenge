package com.example.sportchallenge.domain

import com.example.sportchallenge.data.dao.Workout
import com.example.sportchallenge.data.dao.WorkoutsDAO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class StravaRepository @Inject constructor(
    private val stravaApi: StravaApi,
    private val workoutsDAO: WorkoutsDAO,
) {
    suspend fun postOAthToken(
        clientId: String,
        clientSecret: String,
        code: String
    ): Flow<Response<StravaOAuthResponse>> {
        return flow {
            emit(
                stravaApi.postOAthToken(
                    clientId = clientId,
                    clientSecret = clientSecret,
                    code = code
                )
            )
        }
    }

    suspend fun postOAthRefreshToken(
        clientId: String,
        clientSecret: String,
        code: String
    ): Flow<Response<StravaOAuthResponse>> {
        return flow {
            emit(
                stravaApi.postOAthTokenRefresh(
                    clientId = clientId,
                    clientSecret = clientSecret,
                    refreshToken = code
                )
            )
        }
    }

    suspend fun getStravaActivities(
        token: String,
        before: String,
        after: String,
        page: Int,
        perPage: Int
    ): Flow<Response<List<Workout>>> {
        return flow { emit(stravaApi.getActivities("Bearer $token", before, after, page, perPage)) }
    }

    fun saveStravaActivities(list: List<Workout>) {
        workoutsDAO.insertWorkouts(list)
    }
}