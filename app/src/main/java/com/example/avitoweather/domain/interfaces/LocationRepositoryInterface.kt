package com.example.avitoweather.domain.interfaces

interface LocationRepositoryInterface {
    fun setLocation(lat: String, lon: String, extra: Boolean)
}