package com.example.avitoweather.di

import androidx.lifecycle.ViewModel
import com.example.avitoweather.presentation.viewModels.LocationViewModel
import com.example.avitoweather.presentation.viewModels.WeatherViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(WeatherViewModel::class)
    @Binds
    fun bindWeatherViewModel(impl: WeatherViewModel): ViewModel

    @IntoMap
    @ViewModelKey(LocationViewModel::class)
    @Binds
    fun bindLocationViewModel(impl: LocationViewModel): ViewModel
}