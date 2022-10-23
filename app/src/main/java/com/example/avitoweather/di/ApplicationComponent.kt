package com.example.avitoweather.di

import android.app.Application
import com.example.avitoweather.presentation.MainActivity
import com.example.avitoweather.presentation.fragments.WeatherFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [NetworkModule::class, DataModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun injectApplication(app: Application)
    fun injectMainActivity(mainActivity: MainActivity)
    fun injectWeatherFragment(weatherFragment: WeatherFragment)

    @Component.Factory
    interface Factory{
        fun create(
            @BindsInstance application: Application,
        ): ApplicationComponent
    }
}