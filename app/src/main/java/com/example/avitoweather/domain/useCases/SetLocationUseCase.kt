package com.example.avitoweather.domain.useCases

import com.example.avitoweather.domain.interfaces.LocationRepositoryInterface
import javax.inject.Inject

class SetLocationUseCase @Inject constructor(
    private val locationRepositoryInterface: LocationRepositoryInterface
) {
    operator fun invoke(lat: String, lon:String){
        locationRepositoryInterface.setLocation(lat, lon, false)
    }
}