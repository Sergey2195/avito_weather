package com.example.avitoweather.domain.useCases

import com.example.avitoweather.domain.entites.LocationSuccess
import com.example.avitoweather.domain.interfaces.LocationRepositoryInterface
import javax.inject.Inject

class GetHistoryOfLocationUseCase @Inject constructor(
    private val locationRepositoryInterface: LocationRepositoryInterface
    ){
        suspend operator fun invoke(): List<LocationSuccess>{
            return locationRepositoryInterface.getHistoryOfLocation()
        }
}