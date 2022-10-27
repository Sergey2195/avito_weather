package com.example.avitoweather.data.networkDataSource

import com.example.avitoweather.data.networkDataSource.models.AllDataResponse
import com.example.avitoweather.data.networkDataSource.models.Location
import com.example.avitoweather.data.networkDataSource.models.locationApi.LocationElement
import kotlinx.coroutines.flow.Flow

interface NetworkDatasourceInterface {
    suspend fun loadData(location: Location):AllDataResponse?
    suspend fun loadLocation(resource: String): List<LocationElement>?
    val isLoadingFlow: Flow<Boolean>
    val isErrorFlow: Flow<Boolean>
}