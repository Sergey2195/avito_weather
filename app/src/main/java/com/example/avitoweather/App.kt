package com.example.avitoweather

import android.app.Application
import com.example.avitoweather.di.DaggerApplicationComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class App : Application() {

    //CoroutineScope associated with the lifecycle of an activity
    private val appScope by lazy {
        CoroutineScope(Dispatchers.IO)
    }

    //component for dependency injection
    val component by lazy {
        DaggerApplicationComponent.factory().create(
            this,
            appScope
        )
    }
}