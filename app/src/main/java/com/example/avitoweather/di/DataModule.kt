package com.example.avitoweather.di

import com.example.avitoweather.data.networkDataSource.NetworkDatasource
import com.example.avitoweather.data.networkDataSource.NetworkDatasourceInterface
import com.example.avitoweather.data.repository.LocationRepository
import com.example.avitoweather.data.repository.WeatherRepository
import com.example.avitoweather.domain.interfaces.LocationRepositoryInterface
import com.example.avitoweather.domain.interfaces.WeatherRepositoryInterface
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @Binds
    fun bindNetworkDatasource(impl: NetworkDatasource): NetworkDatasourceInterface

    @Binds
    fun bindWeatherRepository(impl: WeatherRepository): WeatherRepositoryInterface

    @Binds
    fun bindLocationRepository(impl: LocationRepository): LocationRepositoryInterface
}