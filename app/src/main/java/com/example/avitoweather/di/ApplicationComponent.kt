package com.example.avitoweather.di

import android.app.Application
import com.example.avitoweather.di.modules.*
import com.example.avitoweather.presentation.MainActivity
import com.example.avitoweather.presentation.fragments.LocationFragment
import com.example.avitoweather.presentation.fragments.WeatherFragment
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.CoroutineScope

@ApplicationScope
@Component(modules = [WeatherNetworkModule::class, DataModule::class, ViewModelModule::class, LocationNetworkModule::class, LocationDatabaseModule::class])
interface ApplicationComponent {
    fun injectApplication(app: Application)
    fun injectMainActivity(mainActivity: MainActivity)
    fun injectWeatherFragment(weatherFragment: WeatherFragment)
    fun injectLocationFragment(locationFragment: LocationFragment)

    @Component.Factory
    interface Factory{
        fun create(
            @BindsInstance application: Application,
            @BindsInstance applicationScope: CoroutineScope,
        ): ApplicationComponent
    }
}