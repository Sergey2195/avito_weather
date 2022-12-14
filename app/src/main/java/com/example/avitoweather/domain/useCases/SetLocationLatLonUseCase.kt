package com.example.avitoweather.domain.useCases

import com.example.avitoweather.domain.interfaces.LocationRepositoryInterface
import javax.inject.Inject

class SetLocationLatLonUseCase @Inject constructor(
    private val locationRepositoryInterface: LocationRepositoryInterface
) {
    operator fun invoke(lat: String, lon:String, label: String){
        locationRepositoryInterface.addToDatabase(lat, lon, false, label)
    }
}