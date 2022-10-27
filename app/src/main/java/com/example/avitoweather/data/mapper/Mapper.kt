package com.example.avitoweather.data.mapper

import com.example.avitoweather.data.localDataSource.entity.LocationDbEntity
import com.example.avitoweather.data.networkDataSource.models.locationApi.LocationElement
import com.example.avitoweather.domain.entites.LocationSuccess
import javax.inject.Inject

class Mapper @Inject constructor() {
    fun transformLocationDbEntityToLocationSuccess(src: LocationDbEntity): LocationSuccess{
        return LocationSuccess(
            label = src.label,
            lon = src.lon,
            lat = src.lat
        )
    }

    fun transformListLocationDbEntityToLocationSuccess(src: List<LocationDbEntity>): List<LocationSuccess>{
        return src.map { transformLocationDbEntityToLocationSuccess(it) }
    }

    fun transformLocationElementToLocationSuccess(src: LocationElement): LocationSuccess{
        val lon = src.lon?.toString() ?: DEFAULT_LON
        val lat = src.lat?.toString() ?: DEFAULT_LAT
        return LocationSuccess(
            label = src.label,
            lon = lon,
            lat = lat
        )
    }

    fun transformListLocationElementToListLocationSuccess(src: List<LocationElement>): List<LocationSuccess>{
        return src.map { transformLocationElementToLocationSuccess(it) }
    }

    companion object{
        private const val DEFAULT_LAT = "55.741469"
        private const val DEFAULT_LON = "37.615561"
    }
}