package com.example.avitoweather.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.avitoweather.databinding.FragmentWeatherBinding
import com.example.avitoweather.domain.ForecastDay
import com.example.avitoweather.presentation.adapters.ForecastListAdapter

class WeatherFragment : Fragment() {

    private lateinit var binding: FragmentWeatherBinding
    private val forecastAdapter = ForecastListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setupRecyclerView() {
        binding.forecastRv.adapter = forecastAdapter
        binding.forecastRv.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        val exampleList = mutableListOf<ForecastDay>()
        for (i in 0..6){
            exampleList.add(ForecastDay("42",1,2,"sd"))
        }
        forecastAdapter.submitList(exampleList)
    }

    companion object {
        fun newInstance() = WeatherFragment()
    }
}