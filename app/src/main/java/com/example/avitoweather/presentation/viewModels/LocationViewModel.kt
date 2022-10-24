package com.example.avitoweather.presentation.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.avitoweather.domain.entites.LocationState
import com.example.avitoweather.domain.useCases.SetLocationLatLonUseCase
import com.example.avitoweather.domain.useCases.SetLocationStringUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationViewModel @Inject constructor(
    private val setCurrentLocationUseCase: SetLocationLatLonUseCase,
    private val setLocationWithString: SetLocationStringUseCase,
    private val scope: CoroutineScope
) : ViewModel() {

    private val findLocationMutableStateFlow = MutableStateFlow<List<LocationState>?>(null)
    val findLocation: StateFlow<List<LocationState>?> = findLocationMutableStateFlow.asStateFlow()

    fun sendLocation(lat: String, lon: String) {
        setCurrentLocationUseCase.invoke(lat, lon)
    }

    fun findAndSetLocation(str: String){
        scope.launch {
            val result = setLocationWithString.invoke(str)
            findLocationMutableStateFlow.value = result
        }
    }

}