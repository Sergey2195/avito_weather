package com.example.avitoweather.data.networkDataSource.apiCall

import com.example.avitoweather.data.networkDataSource.models.AllDataResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiCalls {
//    @GET("forecast?lat=55.75396&lon=37.620393&extra=false")
    @GET("forecast")
    suspend fun test(
        @Query("lat")lat: String,
        @Query("lon")lon: String,
        @Query("extra")extra: Boolean
    ): AllDataResponse
}