package com.example.sportchallenge.data.dao

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "workouts_list")
data class Workout(
    @PrimaryKey @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("start_date") val date: String,
    var earnedPoints: String?,
    var connectedAppName: String?,
    @SerializedName("type") val sportName: String,
    @SerializedName("moving_time") val movingTime: Int,
) {
    @Ignore val correctFormatDate = date.toFormattedDate()
}

private const val RESPONSE_DATE_FORMAT = "yyyy-MM-DD'T'HH:MM:SS" //2022-05-20T13:27:58Z
private const val NATIVE_DATE_FORMAT = "dd MMMM, yyyy"

private val parser = SimpleDateFormat(RESPONSE_DATE_FORMAT, Locale.ENGLISH)
private val formatter = SimpleDateFormat(NATIVE_DATE_FORMAT, Locale.ENGLISH)
private fun String.toFormattedDate(): String  {
    return if(this.isNotEmpty()) {
        formatter.format(parser.parse(this) as Date).lowercase()
    } else {
        ""
    }
}