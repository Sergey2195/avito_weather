package com.example.avitoweather.data.networkDataSource.models

import com.google.gson.annotations.SerializedName

data class DayShort(
    @SerializedName("temp")
    val temp: Int,

    @SerializedName("temp_min")
    val tempMin: Int,

    @SerializedName("condition")
    val condition: String,

    @SerializedName("icon")
    val icon: String,
)