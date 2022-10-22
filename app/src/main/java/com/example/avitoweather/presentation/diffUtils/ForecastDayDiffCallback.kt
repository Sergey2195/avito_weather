package com.example.avitoweather.presentation.diffUtils

import androidx.recyclerview.widget.DiffUtil
import com.example.avitoweather.domain.ForecastDay

class ForecastDayDiffCallback: DiffUtil.ItemCallback<ForecastDay>() {
    override fun areItemsTheSame(oldItem: ForecastDay, newItem: ForecastDay): Boolean {
        return oldItem.date == newItem.date
    }

    override fun areContentsTheSame(oldItem: ForecastDay, newItem: ForecastDay): Boolean {
        return oldItem == newItem
    }
}