package com.example.avitoweather.presentation.diffUtils

import androidx.recyclerview.widget.DiffUtil
import com.example.avitoweather.domain.entites.CurrentDayWeather
import com.example.avitoweather.domain.entites.HoursForecast

class CurrentDayWeatherDiffCallback: DiffUtil.ItemCallback<HoursForecast>() {

    override fun areItemsTheSame(oldItem: HoursForecast, newItem: HoursForecast): Boolean {
        return oldItem.time == newItem.time
    }

    override fun areContentsTheSame(oldItem: HoursForecast, newItem: HoursForecast): Boolean {
        return oldItem == newItem
    }
}