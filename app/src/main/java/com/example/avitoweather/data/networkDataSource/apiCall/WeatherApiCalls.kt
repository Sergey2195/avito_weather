package com.example.avitoweather.data.networkDataSource.apiCall

import com.example.avitoweather.data.networkDataSource.models.AllDataResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiCalls {
    @GET("forecast")
    suspend fun loadWeather(
        @Query("lat")lat: String,
        @Query("lon")lon: String,
        @Query("extra")extra: Boolean
    ): AllDataResponse
}