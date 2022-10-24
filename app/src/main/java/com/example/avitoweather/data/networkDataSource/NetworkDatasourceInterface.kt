package com.example.avitoweather.data.networkDataSource

import com.example.avitoweather.data.networkDataSource.models.AllDataResponse
import com.example.avitoweather.data.networkDataSource.models.Location
import com.example.avitoweather.data.networkDataSource.models.locationApi.LocationElement

interface NetworkDatasourceInterface {
    suspend fun loadData(location: Location):AllDataResponse
    suspend fun loadLocation(resource: String): LocationElement?
}