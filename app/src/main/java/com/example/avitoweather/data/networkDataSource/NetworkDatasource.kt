package com.example.avitoweather.data.networkDataSource

import com.example.avitoweather.data.networkDataSource.apiCall.LocationApiCalls
import com.example.avitoweather.data.networkDataSource.apiCall.WeatherApiCalls
import com.example.avitoweather.data.networkDataSource.models.AllDataResponse
import com.example.avitoweather.data.networkDataSource.models.Location
import com.example.avitoweather.data.networkDataSource.models.locationApi.LocationElement
import com.example.avitoweather.data.networkDataSource.wrappers.LocationApiKeyWrapper
import com.example.avitoweather.di.ApplicationScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@ApplicationScope
class NetworkDatasource @Inject constructor(
    private val weatherApiCalls: WeatherApiCalls,
    private val locationApiCalls: LocationApiCalls,
    private val locationApiKeyWrapper: LocationApiKeyWrapper,
) : NetworkDatasourceInterface {
    private val isLoadingMutableFlow = MutableStateFlow(false)
    override val isLoadingFlow: Flow<Boolean>
        get() = isLoadingMutableFlow.asStateFlow()

    private val isErrorMutableFlow = MutableStateFlow(false)
    override val isErrorFlow: Flow<Boolean>
        get() = isErrorMutableFlow.asStateFlow()

    override suspend fun loadData(location: Location): AllDataResponse? {
        return try {
            changeValueIsLoadingState(true)
            val result = weatherApiCalls.loadWeather(location.lat, location.lon, location.extra)
            isErrorMutableFlow.value = false
            result
        } catch (e: Exception) {
            isErrorMutableFlow.value = true
            null
        } finally {
            changeValueIsLoadingState(false)
        }
    }

    override suspend fun loadLocation(resource: String): List<LocationElement>? {
        changeValueIsLoadingState(true)
        return try {
            val result = locationApiCalls.loadLocationWithQuery(
                locationApiKeyWrapper.apiKey,
                resource,
                5
            )
            if (result.data.isEmpty()) {
                isErrorMutableFlow.value = true
                return null
            }
            isErrorMutableFlow.value = false
            result.data
        } catch (e: Exception) {
            null
        } finally {
            changeValueIsLoadingState(false)
        }
    }

    private fun changeValueIsLoadingState(state: Boolean) {
        isLoadingMutableFlow.value = state
    }
}