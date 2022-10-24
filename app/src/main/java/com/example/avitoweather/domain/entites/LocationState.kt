package com.example.avitoweather.domain.entites

sealed class LocationState

data class LocationSuccess(
    val label: String,
    val lon: String,
    val lat: String,
): LocationState()

object LocationError: LocationState()