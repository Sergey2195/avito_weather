package com.example.avitoweather.data.networkDataSource.models

import com.google.gson.annotations.SerializedName

data class Parts(
    @SerializedName("day_short")
    val dayShort: DayShort
)