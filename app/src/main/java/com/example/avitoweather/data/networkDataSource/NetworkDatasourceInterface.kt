package com.example.avitoweather.data.networkDataSource

import com.example.avitoweather.data.networkDataSource.models.AllDataResponse
import com.example.avitoweather.data.networkDataSource.models.Location

interface NetworkDatasourceInterface {
    suspend fun loadData(location: Location):AllDataResponse
}