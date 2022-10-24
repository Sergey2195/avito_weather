package com.example.avitoweather

import android.app.Application
import com.example.avitoweather.di.DaggerApplicationComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class App : Application() {

    val appScope by lazy {
        CoroutineScope(Dispatchers.IO)
    }

    val component by lazy {
        DaggerApplicationComponent.factory().create(
            this,
            appScope
        )
    }
}