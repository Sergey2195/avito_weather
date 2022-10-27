package com.example.avitoweather.presentation.viewModels

import androidx.lifecycle.ViewModel
import com.example.avitoweather.domain.entites.LocationState
import com.example.avitoweather.domain.entites.LocationSuccess
import com.example.avitoweather.domain.useCases.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationViewModel @Inject constructor(
    private val setLocationUseCase: SetLocationLatLonUseCase,
    private val getLocationWithString: GetLocationStringUseCase,
    private val scope: CoroutineScope,
    getLoadingStateFlowUseCase: GetLoadingStateFlowUseCase,
    private val getHistoryOfLocationUseCase: GetHistoryOfLocationUseCase,
    private val deleteElementOnHistoryUseCase: DeleteElementOnHistoryUseCase
) : ViewModel() {

    private val findLocationMutableStateFlow = MutableStateFlow<List<LocationState>?>(null)
    val findLocation: StateFlow<List<LocationState>?> = findLocationMutableStateFlow.asStateFlow()
    val isLoadingFlow: Flow<Boolean> = getLoadingStateFlowUseCase.invoke()

    //write Location to data base
    fun sendLocation(lat: String, lon: String, label: String) {
        setLocationUseCase.invoke(lat, lon, label)
    }

    //sends the address as a string and receives a list of found addresses as locationState.
    //writes the resulting list to stateflow. The location fragment is collected and passed to the recyclerView
    fun findAndGetLocation(str: String) {
        scope.launch {
            val result = getLocationWithString.invoke(str)
            findLocationMutableStateFlow.value = result
        }
    }

    //returns history from the database
    suspend fun getHistoryList(): List<LocationSuccess> {
        return getHistoryOfLocationUseCase.invoke()
    }

    //delete from database with label
    suspend fun deleteElementWithLabel(label: String) {
        deleteElementOnHistoryUseCase.invoke(label)
    }

}