package com.example.sportchallenge.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportchallenge.data.dao.ConnectedApp
import com.example.sportchallenge.domain.StravaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val useCase: StravaUseCase) : ViewModel() {

    val testListConnectedApps = listOf(
        ConnectedApp(1, "Apple Health", false),
        ConnectedApp(2, "Samsung Health", false),
        ConnectedApp(3, "Strava", true),
        ConnectedApp(4, "Garmin", false)
    )

    private val _connectedAppsListState = MutableStateFlow<List<ConnectedApp>>(emptyList())
    val connectedAppsListState: StateFlow<List<ConnectedApp>> get() = _connectedAppsListState.asStateFlow()

    private val _collectionActivityStravaState = MutableStateFlow<Boolean>(false)
    val collectionActivityStravaState: StateFlow<Boolean> get() = _collectionActivityStravaState.asStateFlow()

    fun showConnectedAppsList() {
        viewModelScope.launch {
            // add endpoint with connection apps list
            _connectedAppsListState.value = testListConnectedApps
        }
    }

    fun collectDataFromStrava(code: String) {
        viewModelScope.launch {
            useCase.connectStrava(code).collect{
                // if app receive data from strava then collections finish successfully
                _collectionActivityStravaState.value = it
            }
        }
    }
}

interface A {
    var b: Int
}