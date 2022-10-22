package com.example.avitoweather.di

import android.app.Application
import com.example.avitoweather.presentation.MainActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [NetworkModule::class])
interface ApplicationComponent {
    fun injectApplication(app: Application)
    fun injectMainActivity(mainActivity: MainActivity)

    @Component.Factory
    interface Factory{
        fun create(
            @BindsInstance application: Application,
        ): ApplicationComponent
    }
}