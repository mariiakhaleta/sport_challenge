package com.example.sportchallenge.utils.di

import com.example.sportchallenge.data.dao.WorkoutsDAO
import com.example.sportchallenge.domain.StravaApi
import com.example.sportchallenge.domain.StravaInteractor
import com.example.sportchallenge.domain.StravaRepository
import com.example.sportchallenge.domain.StravaUseCase
import com.example.sportchallenge.domain.LatestActivityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideLatestActivityRepository(workoutsDAO: WorkoutsDAO): LatestActivityRepository =
        LatestActivityRepository(workoutsDAO)

    @Provides
    @ViewModelScoped
    fun provideStravaRepository(stravaApi: StravaApi, workoutsDAO: WorkoutsDAO): StravaRepository =
        StravaRepository(stravaApi, workoutsDAO)

    @Provides
    fun provideUsecase(stravaInteractor: StravaInteractor): StravaUseCase =
        stravaInteractor
}