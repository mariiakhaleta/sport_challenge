package com.example.sportchallenge.domain

import android.util.Log
import com.example.sportchallenge.data.dao.Workout
import com.example.sportchallenge.utils.di.currentTimeEpochTimestamp
import com.example.sportchallenge.utils.di.dateOfChallengeStartEpochTimestamp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class StravaInteractor @Inject constructor(private val repository: StravaRepository) : StravaUseCase {

    private val _connected = MutableStateFlow(false)
    private var oneTimeRefreshToken: String = ""
    private var accessToken: String = ""
    private val currentDate = currentTimeEpochTimestamp().toString()
    private val dateOfStartChallenge = dateOfChallengeStartEpochTimestamp()

    override suspend fun connectStrava(code: String): Flow<Boolean> {
        coroutineScope {
            launch(Dispatchers.Default) {
                repository.postOAthToken(
                    clientId = clientId,
                    clientSecret = clientSecret,
                    code = code
                ).collect {
                    Log.d("postOAthTokenRefresh", it.body().toString())
                    if (it.isSuccessful) {
                        oneTimeRefreshToken = it.body()?.refreshToken!!
                    } else {
                        return@collect
                    }
                }

                repository.postOAthRefreshToken(
                    clientId = clientId,
                    clientSecret = clientSecret,
                    code = oneTimeRefreshToken
                ).collect {
                    Log.d("postOAthTokenRefresh", it.body().toString())
                    if (it.isSuccessful) {
                        accessToken = it.body()?.accessToken!!
                    } else {
                        return@collect
                    }
                }

                repository.getStravaActivities(
                    accessToken,
                    currentDate,
                    dateOfStartChallenge, //need to add start and end date of challenge
                    1, //hardcode
                    30 //hardcode
                ).collect{
                    if (it.isSuccessful) {
                        Log.d("Strava Activities", it.body()?.size.toString())

                        val list = it.body()
                        if (!list.isNullOrEmpty()) {
                            val list = it.body()!!.addStravaActivityName()
                            repository.saveStravaActivities(list)
                            _connected.value = true
                        } else {
                            _connected.value = false
                        }
                    }
                }
            }
        }
        return _connected
    }

    private fun List<Workout>.addStravaActivityName(): List<Workout>{
        this.forEach { it.connectedAppName = "Strava" }
        return this
    }

    companion object {
        private const val clientId = "93433"
        private const val clientSecret = "bb936333e93e87b275b81f94bc995f5ed0c218d9"
    }
}