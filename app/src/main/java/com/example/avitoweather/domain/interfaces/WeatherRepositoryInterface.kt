package com.example.avitoweather.domain.interfaces

import com.example.avitoweather.domain.entites.CurrentDayWeather
import com.example.avitoweather.domain.entites.ForecastDay
import com.example.avitoweather.domain.entites.HoursForecast
import kotlinx.coroutines.flow.Flow

interface WeatherRepositoryInterface {
    val currentDayWeatherData: Flow<CurrentDayWeather>
    val forecastWeatherData: Flow<List<ForecastDay>>
    val currentDayForecastList: Flow<List<HoursForecast>>
    val isLoadingError: Flow<Boolean>
    suspend fun loadData()
    suspend fun getTime(): Long
}