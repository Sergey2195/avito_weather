package com.example.avitoweather.domain.useCases

import com.example.avitoweather.domain.entites.LocationState
import com.example.avitoweather.domain.interfaces.LocationRepositoryInterface
import javax.inject.Inject

class GetLocationStringUseCase @Inject constructor(
    private val locationRepositoryInterface: LocationRepositoryInterface
){
    suspend operator fun invoke(str: String): List<LocationState>{
        return locationRepositoryInterface.getLocation(str)
    }
}