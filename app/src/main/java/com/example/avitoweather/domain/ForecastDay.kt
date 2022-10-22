package com.example.avitoweather.domain

data class ForecastDay(
    val date: String,
    val tempMax: Int,
    val tempMin: Int,
    val condition: String
)