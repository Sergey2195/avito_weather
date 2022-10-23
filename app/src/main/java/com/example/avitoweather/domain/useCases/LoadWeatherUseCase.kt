package com.example.avitoweather.domain.useCases

import com.example.avitoweather.domain.interfaces.WeatherRepositoryInterface
import javax.inject.Inject

class LoadWeatherUseCase @Inject constructor(
    private val weatherRepositoryInterface: WeatherRepositoryInterface
) {
    suspend operator fun invoke() {
        weatherRepositoryInterface.loadData()
    }
}