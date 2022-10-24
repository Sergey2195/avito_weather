package com.example.avitoweather.data.networkDataSource.apiCall

import com.example.avitoweather.data.networkDataSource.models.locationApi.LocationApi
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationApiCalls {

    @GET("forward")
    suspend fun loadLocationWithQuery(
        @Query("access_key") key: String,
        @Query("query") location: String,
        @Query("limit") limit: Int
    ): LocationApi
}