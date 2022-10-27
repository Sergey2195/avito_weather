package com.example.avitoweather.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.avitoweather.App
import com.example.avitoweather.R
import com.example.avitoweather.data.networkDataSource.apiCall.WeatherApiCalls
import com.example.avitoweather.databinding.ActivityMainBinding
import com.example.avitoweather.presentation.fragments.LocationFragment
import com.example.avitoweather.presentation.fragments.WeatherFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val component by lazy {
        (application as App).component
    }

    @Inject
    lateinit var weatherApiCalls: WeatherApiCalls

    private lateinit var bottomNavBar: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        component.injectMainActivity(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavigationBottomBar()
        initialFragment()
    }

    //When activity started load weather fragment
    private fun initialFragment() {
        startFragment(WeatherFragment.newInstance(), WeatherFragment.FRAGMENT_NAME)
    }

    //navigation using the bottom navigation bar
    private fun setupNavigationBottomBar() {
        bottomNavBar = binding.bottomNavigationMenu
        bottomNavBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.weatherItem -> {
                    supportFragmentManager.popBackStack(WeatherFragment.FRAGMENT_NAME, 0)
                    true
                }
                R.id.locationBottomItem -> {
                    startFragment(LocationFragment.newInstance(), LocationFragment.FRAGMENT_NAME)
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    //start a fragment and add it to the stack
    private fun startFragment(fragment: Fragment, fragmentName: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFragmentContainer, fragment)
            .addToBackStack(fragmentName)
            .commit()
    }

    //overriding the onBackPressed method. If the back button is pressed while a weatherItem is
    // selected, the application is minimized. If locationFragment, then jump to weatherFragment
    override fun onBackPressed() {
        if (bottomNavBar.selectedItemId == R.id.weatherItem) {
            goHome()
            return
        }
        supportFragmentManager.popBackStack(WeatherFragment.FRAGMENT_NAME, 0)
        bottomNavBar.selectedItemId = R.id.weatherItem
    }

    //minimize app
    private fun goHome() {
        val startMain = Intent(Intent.ACTION_MAIN)
        startMain.addCategory(Intent.CATEGORY_HOME)
        startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(startMain)
    }
}