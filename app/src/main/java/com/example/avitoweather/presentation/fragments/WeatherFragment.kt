package com.example.avitoweather.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.avitoweather.App
import com.example.avitoweather.R
import com.example.avitoweather.databinding.FragmentWeatherBinding
import com.example.avitoweather.domain.entites.CurrentDayWeather
import com.example.avitoweather.presentation.adapters.CurrentTempListAdapter
import com.example.avitoweather.presentation.adapters.ForecastListAdapter
import com.example.avitoweather.presentation.utils.Utils.downloadImage
import com.example.avitoweather.presentation.utils.Utils.formatTemp
import com.example.avitoweather.presentation.viewModels.WeatherViewModel
import com.example.avitoweather.presentation.viewModelsFactory.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherFragment : Fragment() {

    private lateinit var binding: FragmentWeatherBinding

    private val forecastAdapter = ForecastListAdapter()
    private val currentTempAdapter = CurrentTempListAdapter()
    private val component by lazy {
        ((requireActivity().application) as App).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[WeatherViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadData()
        observeCurrentTemperature()
        setupRecyclerViewCurrent()
        setupRecyclerViewForecast()
        observeCurrentDayForecast()
        observeForecastDays()
    }

    private fun observeForecastDays() {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.weatherForecastDays.collect {
                forecastAdapter.submitList(it)
            }
        }
    }

    private fun observeCurrentDayForecast() {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.weatherCurrentDayForecast.collect {
                currentTempAdapter.submitList(it)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.injectWeatherFragment(this)
    }

    private fun observeCurrentTemperature() {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.weatherNowFlow.collect {
                setupCurrentTemperatureUI(it)
            }
        }
    }

    private suspend fun setupCurrentTemperatureUI(data: CurrentDayWeather) =
        withContext(Dispatchers.Main) {
            with(binding) {
                currentTemp.text = formatTemp(data.temp)
                districtTv.text = data.namesOfGeoObject.district
                cityTv.text = data.namesOfGeoObject.locality
                feelsLikeFormatStringTv.text = requireActivity().getString(
                    R.string.feels_like_string_format,
                    formatTemp(data.feelsLike)
                )
                setupIcons(requireContext(), data.icon, currentWeatherIcon)
            }
        }

    private suspend fun setupIcons(context: Context, iconURL: String, icon: ImageView) {
        withContext(Dispatchers.Main) {
            downloadImage(context, iconURL, icon)
        }
    }

    private fun setupRecyclerViewCurrent() {
        binding.currentTempRv.adapter = currentTempAdapter
        binding.currentTempRv.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setupRecyclerViewForecast() {
        binding.forecastRv.adapter = forecastAdapter
        binding.forecastRv.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    companion object {
        fun newInstance() = WeatherFragment()
        const val FRAGMENT_NAME = "WeatherFragment"
    }
}