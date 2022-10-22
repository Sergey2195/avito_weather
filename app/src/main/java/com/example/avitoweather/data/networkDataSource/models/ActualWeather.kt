package com.example.avitoweather.data.networkDataSource.models

import com.google.gson.annotations.SerializedName

data class ActualWeather(
    @SerializedName("temp")
    val temp: Int?,

    @SerializedName("feels_like")
    val feelsLike: Int?,

    @SerializedName("icon")
    val icon: String,

    @SerializedName("condition")
    val condition: String,
)