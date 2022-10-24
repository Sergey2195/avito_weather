package com.example.avitoweather.domain.useCases

import com.example.avitoweather.domain.entites.LocationState
import com.example.avitoweather.domain.interfaces.LocationRepositoryInterface
import javax.inject.Inject

class SetLocationStringUseCase @Inject constructor(
    private val locationRepositoryInterface: LocationRepositoryInterface
){
    suspend operator fun invoke(str: String): LocationState{
        return locationRepositoryInterface.getLocation(str)
    }
}