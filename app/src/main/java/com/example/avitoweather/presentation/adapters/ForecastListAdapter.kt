package com.example.avitoweather.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.avitoweather.databinding.ForecastItemBinding
import com.example.avitoweather.domain.ForecastDay
import com.example.avitoweather.presentation.diffUtils.ForecastDayDiffCallback
import com.example.avitoweather.presentation.viewHolders.ForecastViewHolder

class ForecastListAdapter : ListAdapter<ForecastDay, ForecastViewHolder>(ForecastDayDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ForecastItemBinding.inflate(inflater, parent, false)
        return ForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {

    }
}