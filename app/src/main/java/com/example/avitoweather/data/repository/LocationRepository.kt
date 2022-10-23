package com.example.avitoweather.data.repository

import com.example.avitoweather.data.networkDataSource.models.Location
import com.example.avitoweather.di.ApplicationScope
import com.example.avitoweather.domain.interfaces.LocationRepositoryInterface
import javax.inject.Inject

@ApplicationScope
class LocationRepository @Inject constructor(): LocationRepositoryInterface {
    private var _location: Location = Location()

    val location: Location
        get() = _location

    override fun setLocation(lat: String, lon: String, extra: Boolean) {
        _location = Location(lat, lon, extra)
    }
}