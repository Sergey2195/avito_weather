package com.example.avitoweather.domain.useCases

import com.example.avitoweather.domain.interfaces.LocationRepositoryInterface
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLoadingStateFlowUseCase @Inject constructor(
    private val locationRepositoryInterface: LocationRepositoryInterface
) {
    operator fun invoke(): Flow<Boolean>{
        return locationRepositoryInterface.isLoadingFlow
    }
}