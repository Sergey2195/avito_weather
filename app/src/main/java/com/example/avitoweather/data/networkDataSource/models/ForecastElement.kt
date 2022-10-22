package com.example.avitoweather.data.networkDataSource.models

import com.google.gson.annotations.SerializedName

data class ForecastElement(
    @SerializedName("date")
    val date: String?,

    @SerializedName("parts")
    val parts: Parts
)