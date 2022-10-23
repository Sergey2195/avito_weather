package com.example.avitoweather.domain.entites

data class HoursForecast(
    val time: String,
    val timeTS: Long,
    val icon: String,
    val temp: Int,
)