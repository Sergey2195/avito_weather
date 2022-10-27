package com.example.avitoweather.domain.interfaces

import androidx.lifecycle.LiveData
import com.example.avitoweather.domain.entites.LocationState
import com.example.avitoweather.domain.entites.LocationSuccess
import kotlinx.coroutines.flow.Flow

interface LocationRepositoryInterface {
    fun addToDatabase(lat: String, lon: String, extra: Boolean, label: String)
    suspend fun getLocation(requestStr: String): List<LocationState>
    val isLoadingFlow: Flow<Boolean>
    suspend fun getHistoryOfLocation(): List<LocationSuccess>
    suspend fun deleteElementOnHistory(label: String)
}