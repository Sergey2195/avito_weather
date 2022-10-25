package com.example.avitoweather.domain.interfaces

import com.example.avitoweather.domain.entites.LocationState
import kotlinx.coroutines.flow.Flow

interface LocationRepositoryInterface {
    fun setLocation(lat: String, lon: String, extra: Boolean)
    suspend fun getLocation(requestStr: String): List<LocationState>
    val isLoadingFlow: Flow<Boolean>
}