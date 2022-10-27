package com.example.avitoweather.domain.entites

data class CurrentDayWeather(
    val time: String = "0",
    val icon: String = "",
    val temp: Int = 0,
    val feelsLike: Int = 0,
    val namesOfGeoObject: NamesOfGeoObject = NamesOfGeoObject()
)