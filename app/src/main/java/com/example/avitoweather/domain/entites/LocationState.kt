package com.example.avitoweather.domain.entites

sealed class LocationState

data class LocationSuccess(
    val label: String
): LocationState()

object LocationError: LocationState()