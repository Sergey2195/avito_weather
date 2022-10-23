package com.example.avitoweather.data.repository

import android.util.Log
import com.example.avitoweather.data.networkDataSource.NetworkDatasourceInterface
import com.example.avitoweather.data.networkDataSource.models.AllDataResponse
import com.example.avitoweather.data.networkDataSource.models.ForecastElement
import com.example.avitoweather.data.networkDataSource.models.HourElement
import com.example.avitoweather.di.ApplicationScope
import com.example.avitoweather.domain.entites.CurrentDayWeather
import com.example.avitoweather.domain.entites.ForecastDay
import com.example.avitoweather.domain.entites.HoursForecast
import com.example.avitoweather.domain.entites.NamesOfGeoObject
import com.example.avitoweather.domain.interfaces.WeatherRepositoryInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@ApplicationScope
class WeatherRepository @Inject constructor(
    private val networkDatasource: NetworkDatasourceInterface,
    private val locationRepository: LocationRepository
) : WeatherRepositoryInterface {

    private val currentDayWeatherDataMutableFlow =
        MutableStateFlow(CurrentDayWeather("", "", 0, 0, NamesOfGeoObject("", "")))
    override val currentDayWeatherData: Flow<CurrentDayWeather>
        get() = currentDayWeatherDataMutableFlow.asStateFlow()

    private val forecastWeatherDataMutableStateFlow = MutableStateFlow(emptyList<ForecastDay>())
    override val forecastWeatherData: Flow<List<ForecastDay>>
        get() = forecastWeatherDataMutableStateFlow.asStateFlow()

    private val currentDayForecastListMutableStateFlow =
        MutableStateFlow(emptyList<HoursForecast>())
    override val currentDayForecastList: Flow<List<HoursForecast>>
        get() = currentDayForecastListMutableStateFlow.asStateFlow()

    private var nowTime: Long = 0

    override suspend fun loadData() {
        try {
            val result = networkDatasource.loadData(locationRepository.location)
            if (result.actualWeather == null) return
            setupNowTime(result.nowTime ?: 0)
            val namesOfGeoObject = NamesOfGeoObject(
                result.geoObject?.locality?.name.toString(),
                result.geoObject?.district?.name.toString()
            )
            val actualWeather = CurrentDayWeather(
                time = result.nowTime.toString(),
                feelsLike = result.actualWeather.feelsLike ?: 0,
                icon = result.actualWeather.icon,
                temp = result.actualWeather.temp ?: 0,
                namesOfGeoObject = namesOfGeoObject
            )
            currentDayForecastListMutableStateFlow.value = configureHoursForecast(result)
            currentDayWeatherDataMutableFlow.value = actualWeather

            val listForecastDays = transformListForecastToForecastWeather(result.forecast)
            forecastWeatherDataMutableStateFlow.value = listForecastDays
            Log.i("TAG", "loadData finished")
        } catch (e: Exception) {
            throw RuntimeException("load data exception $e")
        }
    }

    private fun transformHourElementToHourForecast(item: HourElement): HoursForecast{
        return HoursForecast(
            item.hour,
            item.hourTS,
            item.icon,
            item.temp
        )
    }

    private fun configureHoursForecast(result: AllDataResponse): List<HoursForecast>{
        val twoDaysList = mutableListOf<HoursForecast>()
        result.forecast[0].hourElement.forEach{ item->
            if (item.hourTS >= nowTime){
                twoDaysList.add(transformHourElementToHourForecast(item))
            }
        }
        result.forecast[1].hourElement.forEach{ item->
            if (twoDaysList.size <= 24){
                twoDaysList.add(transformHourElementToHourForecast(item))
            }
        }
        return twoDaysList
    }

    override suspend fun getTime(): Long {
        return nowTime
    }

    private fun setupNowTime(currentTime: Long){
        nowTime = currentTime
    }

    private fun transformListForecastToForecastWeather(src: List<ForecastElement>): List<ForecastDay> {
        val result = mutableListOf<ForecastDay>()
        src.forEach { item ->
            val newElement = ForecastDay(
                date = item.date ?: "00-00-00",
                tempMax = item.parts.dayShort.temp,
                tempMin = item.parts.dayShort.tempMin,
                condition = item.parts.dayShort.condition,
                icon = item.parts.dayShort.icon
            )
            result.add(newElement)
        }
        return result
    }
}