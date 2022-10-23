package com.example.avitoweather.domain.useCases

import com.example.avitoweather.domain.interfaces.WeatherRepositoryInterface
import javax.inject.Inject

class GetTimeUseCase @Inject constructor(
    private val weatherRepositoryInterface: WeatherRepositoryInterface
) {
    suspend operator fun invoke(): Long {
        return weatherRepositoryInterface.getTime()
    }
}