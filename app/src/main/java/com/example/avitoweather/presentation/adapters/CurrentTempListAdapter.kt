package com.example.avitoweather.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.avitoweather.R
import com.example.avitoweather.databinding.CurrentTempItemBinding
import com.example.avitoweather.domain.entites.HoursForecast
import com.example.avitoweather.presentation.diffUtils.CurrentDayWeatherDiffCallback
import com.example.avitoweather.presentation.viewHolders.CurrentTempViewHolder
import com.example.avitoweather.utils.Utils.downloadImage
import com.example.avitoweather.utils.Utils.formatTemp

class CurrentTempListAdapter :
    ListAdapter<HoursForecast, CurrentTempViewHolder>(CurrentDayWeatherDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrentTempViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CurrentTempItemBinding.inflate(inflater, parent, false)
        return CurrentTempViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrentTempViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            timeCurrentTemp.text = item.time
            tempCurrentTemp.text = formatTemp(item.temp)
        }
        downloadImage(holder.itemView.context, item.icon, holder.binding.weatherIconCurrentTemp)
        val id: Int
        when (getItemViewType(position)) {
            FIRST_ITEM -> id = R.drawable.left_rounded
            LAST_ITEM -> id = R.drawable.right_rounded
            else -> id = R.color.secondaryColor
        }
        holder.binding.currentTempLayout.setBackgroundResource(id)
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> FIRST_ITEM
            currentList.lastIndex -> LAST_ITEM
            else -> INTERMEDIATE_ITEM
        }
    }

    companion object {
        private const val LAST_ITEM = 2
        private const val INTERMEDIATE_ITEM = 1
        private const val FIRST_ITEM = 0
    }
}