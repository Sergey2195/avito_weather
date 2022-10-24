package com.example.avitoweather.domain.interfaces

import com.example.avitoweather.domain.entites.LocationState

interface LocationRepositoryInterface {
    fun setLocation(lat: String, lon: String, extra: Boolean)
    suspend fun getLocation(requestStr: String): LocationState
}