package com.example.avitoweather.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.avitoweather.App
import com.example.avitoweather.R
import com.example.avitoweather.data.networkDataSource.apiCall.ApiCalls
import com.example.avitoweather.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val component by lazy {
        (application as App).component
    }

    @Inject
    lateinit var apiCalls: ApiCalls

    private lateinit var bottomNavBar: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        component.injectMainActivity(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavigationBottomBar()
        initialFragment()
//        CoroutineScope(Dispatchers.IO).launch {
//            val res = apiCalls.test("55.75396", "37.620393", false)
//            Log.i("TEST_CURRENT TIME in utc", res.nowInUTC.toString())
//            Log.i("TEST_ACTUAL", "${res.actualWeather?.toString()}")
//            Log.i("TEST_FORECAST", "${res.forecast}")
//        }
    }

    private fun initialFragment() {
        startFragment(WeatherFragment.newInstance())
    }

    private fun setupNavigationBottomBar() {
        bottomNavBar = binding.bottomNavigationMenu
        bottomNavBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.weatherItem -> {
                    startFragment(WeatherFragment.newInstance())
                    true
                }
                R.id.navigationItem -> {
                    startFragment(LocationFragment.newInstance())
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
    private fun startFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFragmentContainer, fragment)
            .commit()
    }
}