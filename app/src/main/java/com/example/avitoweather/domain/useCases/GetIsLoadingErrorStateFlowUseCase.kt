package com.example.avitoweather.domain.useCases

import com.example.avitoweather.domain.interfaces.WeatherRepositoryInterface
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetIsLoadingErrorStateFlowUseCase @Inject constructor(
    private val weatherRepositoryInterface: WeatherRepositoryInterface
) {
    operator fun invoke(): Flow<Boolean> {
        return weatherRepositoryInterface.isLoadingError
    }
}