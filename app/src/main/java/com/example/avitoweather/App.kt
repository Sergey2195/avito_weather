package com.example.avitoweather

import android.app.Application
import com.example.avitoweather.di.DaggerApplicationComponent

class App : Application() {
    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}