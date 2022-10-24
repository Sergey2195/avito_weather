package com.example.avitoweather.data.repository

import com.example.avitoweather.data.networkDataSource.NetworkDatasourceInterface
import com.example.avitoweather.data.networkDataSource.models.Location
import com.example.avitoweather.di.ApplicationScope
import com.example.avitoweather.domain.entites.LocationError
import com.example.avitoweather.domain.entites.LocationState
import com.example.avitoweather.domain.entites.LocationSuccess
import com.example.avitoweather.domain.interfaces.LocationRepositoryInterface
import com.example.avitoweather.utils.Utils
import javax.inject.Inject

@ApplicationScope
class LocationRepository @Inject constructor(
    private val networkDatasource: NetworkDatasourceInterface
): LocationRepositoryInterface {
    private var _location: Location = Location()

    val location: Location
        get() = _location

    override fun setLocation(lat: String, lon: String, extra: Boolean) {
        _location = Location(lat, lon, extra)
    }

    override suspend fun getLocation(requestStr: String): LocationState{
        val result = networkDatasource.loadLocation(requestStr) ?: return LocationError
        if (result.lon == null || result.lat == null) return LocationError
        setLocation(result.lat.toString(), result.lon.toString(), false)
        return LocationSuccess(result.label)
    }
}