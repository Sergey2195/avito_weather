package com.example.avitoweather.data.networkDataSource.models.geoObject

import com.google.gson.annotations.SerializedName

data class GeoObject(
    @SerializedName("district")
    val district: NameGeoObject,

    @SerializedName("locality")
    val locality: NameGeoObject
)