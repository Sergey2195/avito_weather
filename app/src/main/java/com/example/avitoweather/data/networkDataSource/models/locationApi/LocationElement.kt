package com.example.avitoweather.data.networkDataSource.models.locationApi

import com.google.gson.annotations.SerializedName

data class LocationElement(
    @SerializedName("latitude")
    val lat: Double?,

    @SerializedName("longitude")
    val lon: Double?,

    @SerializedName("label")
    val label: String
)