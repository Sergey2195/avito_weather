package com.example.avitoweather.presentation.viewModels

import androidx.lifecycle.ViewModel
import com.example.avitoweather.domain.useCases.SetLocationUseCase
import javax.inject.Inject

class LocationViewModel @Inject constructor(
    private val setLocationUseCase: SetLocationUseCase
) : ViewModel() {

    fun sendLocation(lat: String, lon: String) {
        setLocationUseCase.invoke(lat, lon)
    }
}