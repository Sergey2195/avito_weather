package com.example.avitoweather.data.networkDataSource.models

import com.example.avitoweather.data.networkDataSource.models.geoObject.GeoObject
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class AllDataResponse(
    @SerializedName("now")
    val nowTime: Long?,

    @SerializedName("now_dt")
    val nowInUTC: String?,

    @SerializedName("info")
    val info: JsonObject?,

    @SerializedName("geo_object")
    val geoObject: GeoObject?,

    @SerializedName("yesterday")
    val yesterday: JsonObject?,

    @SerializedName("fact")
    val actualWeather: ActualWeather?,

    @SerializedName("forecasts")
    val forecast: List<ForecastElement>
)