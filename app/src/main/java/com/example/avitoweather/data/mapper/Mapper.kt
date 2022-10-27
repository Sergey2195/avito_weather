package com.example.avitoweather.data.mapper

import com.example.avitoweather.data.localDataSource.entity.LocationDbEntity
import com.example.avitoweather.data.networkDataSource.models.AllDataResponse
import com.example.avitoweather.data.networkDataSource.models.locationApi.LocationElement
import com.example.avitoweather.domain.entites.CurrentDayWeather
import com.example.avitoweather.domain.entites.LocationSuccess
import com.example.avitoweather.domain.entites.NamesOfGeoObject
import javax.inject.Inject

class Mapper @Inject constructor() {
    fun transformLocationDbEntityToLocationSuccess(src: LocationDbEntity): LocationSuccess {
        return LocationSuccess(
            label = src.label,
            lon = src.lon,
            lat = src.lat
        )
    }

    fun transformListLocationDbEntityToLocationSuccess(src: List<LocationDbEntity>): List<LocationSuccess> {
        return src.map { transformLocationDbEntityToLocationSuccess(it) }
    }

    fun transformLocationElementToLocationSuccess(src: LocationElement): LocationSuccess {
        val lon = src.lon?.toString() ?: DEFAULT_LON
        val lat = src.lat?.toString() ?: DEFAULT_LAT
        return LocationSuccess(
            label = src.label,
            lon = lon,
            lat = lat
        )
    }

    fun transformListLocationElementToListLocationSuccess(src: List<LocationElement>): List<LocationSuccess> {
        return src.map { transformLocationElementToLocationSuccess(it) }
    }

    fun transformAllDataResponseToCurrentDayWeather(result: AllDataResponse): CurrentDayWeather {
        return CurrentDayWeather(
            time = result.nowTime.toString(),
            icon = result.actualWeather?.icon ?: "",
            temp = result.actualWeather?.temp ?: 0,
            feelsLike = result.actualWeather?.feelsLike ?: 0,
            namesOfGeoObject = NamesOfGeoObject(
                locality = result.geoObject?.locality?.name ?: "",
                district = result.geoObject?.district?.name ?: "",
                province = result.geoObject?.province?.name ?: "",
                country = result.geoObject?.county?.name ?: ""
            )
        )
    }

    companion object {
        private const val DEFAULT_LAT = "55.741469"
        private const val DEFAULT_LON = "37.615561"
    }
}