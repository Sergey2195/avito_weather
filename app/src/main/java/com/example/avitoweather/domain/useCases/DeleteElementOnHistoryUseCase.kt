package com.example.avitoweather.domain.useCases

import com.example.avitoweather.domain.interfaces.LocationRepositoryInterface
import javax.inject.Inject

class DeleteElementOnHistoryUseCase @Inject constructor(
    private val locationRepositoryInterface: LocationRepositoryInterface
) {
    suspend operator fun invoke(label: String){
        locationRepositoryInterface.deleteElementOnHistory(label)
    }
}