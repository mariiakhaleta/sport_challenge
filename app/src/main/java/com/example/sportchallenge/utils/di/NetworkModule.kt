package com.example.sportchallenge.utils.di
import android.content.Context
import androidx.room.Room
import com.example.sportchallenge.BuildConfig
import com.example.sportchallenge.data.dao.AppDatabase
import com.example.sportchallenge.domain.StravaApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*
import javax.inject.Singleton

const val STRAVA_URL: String = "https://www.strava.com/api/v3/"
const val BASE_URL: String = "base_url"

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    fun providesGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(STRAVA_URL)
        .client(
            OkHttpClient.Builder().also { client ->
                if (BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor()
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                    client.addInterceptor(logging)
                }
                client.protocols(Collections.singletonList(Protocol.HTTP_1_1))
            }.build()
        )
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    @Singleton
    fun provideStravaApi(retrofit: Retrofit): StravaApi = retrofit.create(StravaApi::class.java)


    @Singleton
    @Provides
    fun provideYourDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "database"
    ).build()

    @Singleton
    @Provides
    fun provideRepositoryDao(db: AppDatabase) = db.workoutsDAO()
}