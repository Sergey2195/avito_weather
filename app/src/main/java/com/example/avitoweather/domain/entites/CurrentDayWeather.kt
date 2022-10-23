package com.example.avitoweather.domain.entites

data class CurrentDayWeather(
    val time: String,
    val icon: String,
    val temp: Int,
    val feelsLike: Int,
    val namesOfGeoObject: NamesOfGeoObject
)