package com.example.avitoweather.data.networkDataSource.models.locationApi

import com.google.gson.annotations.SerializedName

data class LocationApi(
    @SerializedName("data")
    val data: List<LocationElement>
)