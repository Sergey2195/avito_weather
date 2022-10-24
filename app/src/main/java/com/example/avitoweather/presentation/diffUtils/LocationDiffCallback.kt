package com.example.avitoweather.presentation.diffUtils

import androidx.recyclerview.widget.DiffUtil
import com.example.avitoweather.domain.entites.LocationSuccess

class LocationDiffCallback: DiffUtil.ItemCallback<LocationSuccess>() {
    override fun areItemsTheSame(oldItem: LocationSuccess, newItem: LocationSuccess): Boolean {
        return oldItem.label == newItem.label
    }

    override fun areContentsTheSame(oldItem: LocationSuccess, newItem: LocationSuccess): Boolean {
        return oldItem == newItem
    }
}