package com.example.avitoweather.data.networkDataSource.models.geoObject

import com.google.gson.annotations.SerializedName

data class NameGeoObject(
    @SerializedName("name")
    val name: String
)