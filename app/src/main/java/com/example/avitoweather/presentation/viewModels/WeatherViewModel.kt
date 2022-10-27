package com.example.avitoweather.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.avitoweather.domain.interfaces.WeatherRepositoryInterface
import com.example.avitoweather.domain.useCases.GetIsLoadingErrorStateFlowUseCase
import com.example.avitoweather.domain.useCases.GetLoadingStateFlowUseCase
import com.example.avitoweather.domain.useCases.LoadWeatherUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
    private val loadWeatherUseCase: LoadWeatherUseCase,
    private val isLoadingErrorStateFlowUseCase: GetIsLoadingErrorStateFlowUseCase,
    weatherRepositoryInterface: WeatherRepositoryInterface,
    getLoadingStateFlowUseCase: GetLoadingStateFlowUseCase,
) : ViewModel() {

    val isLoadingFlow = getLoadingStateFlowUseCase.invoke()
    val weatherNowFlow = weatherRepositoryInterface.currentDayWeatherData
    val weatherCurrentDayForecast = weatherRepositoryInterface.currentDayForecastList
    val weatherForecastDays = weatherRepositoryInterface.forecastWeatherData

    //loading weather
    fun loadData(){
        viewModelScope.launch(Dispatchers.IO){
            loadWeatherUseCase.invoke()
        }
    }

    //flow to track the occurrence of an error during the download
    fun getIsLoadingErrorFlow(): Flow<Boolean>{
        return isLoadingErrorStateFlowUseCase.invoke()
    }
}