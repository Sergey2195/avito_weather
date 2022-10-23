package com.example.avitoweather.data.networkDataSource.models

import com.google.gson.annotations.SerializedName

data class HourElement(
    @SerializedName("hour")
    val hour: String,

    @SerializedName("hour_ts")
    val hourTS: Long,

    @SerializedName("temp")
    val temp: Int,

    @SerializedName("icon")
    val icon: String
)