package com.example.avitoweather.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.avitoweather.App
import com.example.avitoweather.R
import com.example.avitoweather.databinding.FragmentWeatherBinding
import com.example.avitoweather.domain.entites.CurrentDayWeather
import com.example.avitoweather.presentation.adapters.CurrentTempListAdapter
import com.example.avitoweather.presentation.adapters.ForecastListAdapter
import com.example.avitoweather.presentation.viewModels.WeatherViewModel
import com.example.avitoweather.presentation.viewModelsFactory.ViewModelFactory
import com.example.avitoweather.utils.Utils.downloadImage
import com.example.avitoweather.utils.Utils.formatProvinceAndCountry
import com.example.avitoweather.utils.Utils.formatTemp
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
        initial()
        viewModel.loadData()
        setupRefreshLayout()
        observeCurrentTemperature()
        setupRecyclerViewCurrent()
        setupRecyclerViewForecast()
        observeCurrentDayForecast()
        observeForecastDays()
        observeLoading()
    }

    private fun initial() {
        changeVisibility(false)
    }

    //setup refresh layout
    private fun setupRefreshLayout() {
        binding.refreshLayout.setOnRefreshListener {
            viewModel.loadData()
        }
    }

    //collects data to display the weather forecast for the current day and sends it to views
    private fun observeCurrentTemperature() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.weatherNowFlow.collect {
                setupCurrentTemperatureUI(it)
            }
        }
    }

    //setting the desired data for the current day
    private suspend fun setupCurrentTemperatureUI(data: CurrentDayWeather) =
        withContext(Dispatchers.Main) {
            with(binding) {
                cityTv.text = data.namesOfGeoObject.locality
                configureDistrict(data.namesOfGeoObject.district)
                currentTemp.text = formatTemp(data.temp)
                provinceAndCountryTv.text = formatProvinceAndCountry(
                    data.namesOfGeoObject.province,
                    data.namesOfGeoObject.country
                )
                feelsLikeFormatStringTv.text = requireActivity().getString(
                    R.string.feels_like_string_format,
                    formatTemp(data.feelsLike)
                )
                setupIcons(requireContext(), data.icon, currentWeatherIcon)
            }
        }


    //collects data to display a 7-day weather forecast and sends it to RecyclerView
    private fun observeForecastDays() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.weatherForecastDays.collect {
                forecastAdapter.submitList(it)
            }
        }
    }

    //collects data to display the weather forecast for the current day and sends it to RecyclerView
    private fun observeCurrentDayForecast() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.weatherCurrentDayForecast.collect {
                currentTempAdapter.submitList(it)
                scrollToBegin()
            }
        }
    }

    //rewinds the recycler view to its starting position
    private suspend fun scrollToBegin() {
        withContext(Dispatchers.Main) {
            delay(50)
            binding.currentTempRv.smoothScrollToPosition(0)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.injectWeatherFragment(this)
    }

    private fun configureDistrict(district: String) {
        if (district.isEmpty()) {
            binding.districtTv.visibility = View.GONE
        } else {
            binding.districtTv.visibility = View.VISIBLE
            binding.districtTv.text = district
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

    //tracking the end of the download and the occurrence of an error.In case of an error, a snackbar is shown
    private fun observeLoading() {
        lifecycleScope.launch {
            viewModel.isLoadingFlow.collect {
                viewModel.getIsLoadingErrorFlow()
                changeVisibility(!it)
            }
        }
        lifecycleScope.launch {
            viewModel.getIsLoadingErrorFlow().collect { isError ->
                if (isError) {
                    binding.baseConstraintLayout.visibility = View.INVISIBLE
                    showSnackbarError()
                }else{
                    binding.baseConstraintLayout.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun showSnackbarError() {
        if (view != null) {
            Snackbar.make(requireView(), getString(R.string.error_loading), Snackbar.LENGTH_LONG)
                .show()
        }
    }

    private fun changeVisibility(state: Boolean) {
        with(binding) {
            cityTv.isVisible = state
            districtTv.isVisible = state
            currentTemp.isVisible = state
            currentWeatherIcon.isVisible = state
            feelsLikeFormatStringTv.isVisible = state
            titleCurrentTemp.isVisible = state
            currentTempRv.isVisible = state
            forecastRv.isVisible = state
            forecastTitle.isVisible = state
            minTitle.isVisible = state
            maxTitle.isVisible = state
            provinceAndCountryTv.isVisible = state
            refreshLayout.isRefreshing = !state
        }
    }

    companion object {
        fun newInstance() = WeatherFragment()
        const val FRAGMENT_NAME = "WeatherFragment"
    }
}