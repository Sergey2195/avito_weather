package com.example.avitoweather.presentation.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.avitoweather.domain.interfaces.WeatherRepositoryInterface
import com.example.avitoweather.domain.useCases.GetTimeUseCase
import com.example.avitoweather.domain.useCases.LoadWeatherUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
    private val loadWeatherUseCase: LoadWeatherUseCase,
    weatherRepositoryInterface: WeatherRepositoryInterface,
) : ViewModel() {

    val weatherNowFlow = weatherRepositoryInterface.currentDayWeatherData
    val weatherCurrentDayForecast = weatherRepositoryInterface.currentDayForecastList
    val weatherForecastDays = weatherRepositoryInterface.forecastWeatherData

    fun loadData(){
        viewModelScope.launch(Dispatchers.IO){
            loadWeatherUseCase.invoke()
        }
    }
}