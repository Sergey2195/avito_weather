package com.example.avitoweather.data.networkDataSource

import com.example.avitoweather.data.networkDataSource.apiCall.LocationApiCalls
import com.example.avitoweather.data.networkDataSource.apiCall.WeatherApiCalls
import com.example.avitoweather.data.networkDataSource.models.AllDataResponse
import com.example.avitoweather.data.networkDataSource.models.Location
import com.example.avitoweather.data.networkDataSource.models.locationApi.LocationElement
import com.example.avitoweather.data.networkDataSource.wrappers.LocationApiKeyWrapper
import com.example.avitoweather.di.ApplicationScope
import javax.inject.Inject

@ApplicationScope
class NetworkDatasource @Inject constructor(
    private val weatherApiCalls: WeatherApiCalls,
    private val locationApiCalls: LocationApiCalls,
    private val locationApiKeyWrapper: LocationApiKeyWrapper
): NetworkDatasourceInterface {
    override suspend fun loadData(location: Location): AllDataResponse {
        return weatherApiCalls.loadWeather(location.lat, location.lon, location.extra)
    }

    override suspend fun loadLocation(resource: String): LocationElement?{
        return try {
            val result = locationApiCalls.loadLocationWithQuery(
                locationApiKeyWrapper.apiKey,
                resource,
                1)
            if (result.data.isEmpty()) null else result.data[0]
        }catch (e: Exception){
            null
        }
    }
}