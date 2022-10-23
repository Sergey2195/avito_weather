package com.example.avitoweather.data.networkDataSource

import com.example.avitoweather.data.networkDataSource.apiCall.ApiCalls
import com.example.avitoweather.data.networkDataSource.models.AllDataResponse
import com.example.avitoweather.data.networkDataSource.models.Location
import com.example.avitoweather.di.ApplicationScope
import javax.inject.Inject

@ApplicationScope
class NetworkDatasource @Inject constructor(
    private val apiCalls: ApiCalls
): NetworkDatasourceInterface {
    override suspend fun loadData(location: Location): AllDataResponse {
        return apiCalls.loadWeather(location.lat, location.lon, location.extra)
    }
}