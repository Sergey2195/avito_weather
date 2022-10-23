package com.example.avitoweather.domain.entites

data class ForecastDay(
    val date: String,
    val tempMax: Int,
    val tempMin: Int,
    val condition: String,
    val icon: String,
)


