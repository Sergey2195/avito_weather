package com.example.avitoweather.data.repository

import com.example.avitoweather.data.networkDataSource.NetworkDatasourceInterface
import com.example.avitoweather.data.networkDataSource.models.Location
import com.example.avitoweather.di.ApplicationScope
import com.example.avitoweather.domain.entites.LocationError
import com.example.avitoweather.domain.entites.LocationState
import com.example.avitoweather.domain.entites.LocationSuccess
import com.example.avitoweather.domain.interfaces.LocationRepositoryInterface
import com.example.avitoweather.utils.Utils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ApplicationScope
class LocationRepository @Inject constructor(
    private val networkDatasource: NetworkDatasourceInterface,
): LocationRepositoryInterface {
    private var _location: Location = Location()

    val location: Location
        get() = _location

    override val isLoadingFlow: Flow<Boolean> = networkDatasource.isLoadingFlow

    override fun setLocation(lat: String, lon: String, extra: Boolean) {
        _location = Location(lat, lon, extra)
    }

    override suspend fun getLocation(requestStr: String): List<LocationState>{
        val result = networkDatasource.loadLocation(requestStr) ?: return listOf(LocationError)
        val list = mutableListOf<LocationSuccess>()
        for (element in result){
            list.add(LocationSuccess(element.label, element.lon.toString(), element.lat.toString()))
        }
        return list.toList()
    }
}