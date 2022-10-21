package com.example.sportchallenge.domain

import com.example.sportchallenge.data.dao.Workout
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

const val POST_OAUTH_TOKEN: String = "oauth/token"
const val GET_ACTIVITIES: String = "athlete/activities"

interface StravaApi {

    //  curl -X POST https://www.strava.com/api/v3/oauth/token \
    //  -d client_id=ReplaceWithClientID \
    //  -d client_secret=ReplaceWithClientSecret \
    //  -d code=ReplaceWithCode \
    //  -d grant_type=authorization_code

    @POST(POST_OAUTH_TOKEN)
    suspend fun postOAthToken(
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("code") code: String,
        @Query("grant_type") grandType: String = "authorization_code"
    ): Response<StravaOAuthResponse>

    @POST(POST_OAUTH_TOKEN)
    suspend fun postOAthTokenRefresh(
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("grant_type") grandType: String = "refresh_token",
        @Query("refresh_token") refreshToken: String,
    ): Response<StravaOAuthResponse>

    // $ http GET "https://www.strava.com/api/v3/athlete/activities?before=&after=&page=&per_page="
    // "Authorization: Bearer [[token]]"
    @GET(GET_ACTIVITIES)
    suspend fun getActivities(
        @Header("Authorization") token: String,
        @Query("before") before: String,
        @Query("after") after: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): Response<List<Workout>>
}

// {"token_type":"Bearer","expires_at":1663346668,"expires_in":21381,
// "refresh_token":"b7b45e8d786f458deda0c58047d381576dfaa2af","access_token":"15bf734ff29c2da1c88c46ece55f944e5bd6d175",
// "athlete":{"id":96879874,"username":"mkhaleta","resource_state":2,"firstname":"Maria","lastname":"Khaleta",
// "bio":null,"city":null,"state":null,"country":null,"sex":"F","premium":false,"summit":false,
// "created_at":"2021-12-30T17:16:06Z","updated_at":"2022-01-03T15:12:23Z","badge_type_id":0,"weight":0.0,
// "profile_medium":"https://lh3.googleusercontent.com/a-/AFdZucpHB8n9tiZpR06iVaptJ4fDZQoRToaVlWg_hJlT_LA=s96-c",
// "profile":"https://lh3.googleusercontent.com/","friend":null,"follower":null}}
data class StravaOAuthResponse(
    @SerializedName("token_type") val tokenType: String,
    @SerializedName("expires_at") val expiresAt: Int,
    @SerializedName("expires_in") val expiresIn: Int,
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("athlete") val athlete: Athlete,
)

data class Athlete(
    @SerializedName("id") val id: Int,
    @SerializedName("username") val userName: String,
    @SerializedName("firstname") val firstName: String,
    @SerializedName("lastname") val lastName: String,
)