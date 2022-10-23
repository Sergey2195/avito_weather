package com.example.avitoweather.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.avitoweather.R
import com.example.avitoweather.databinding.ForecastItemBinding
import com.example.avitoweather.domain.entites.ForecastDay
import com.example.avitoweather.presentation.diffUtils.ForecastDayDiffCallback
import com.example.avitoweather.utils.Utils.downloadImage
import com.example.avitoweather.utils.Utils.formatDate
import com.example.avitoweather.presentation.viewHolders.ForecastViewHolder

class ForecastListAdapter :
    ListAdapter<ForecastDay, ForecastViewHolder>(ForecastDayDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ForecastItemBinding.inflate(inflater, parent, false)
        return ForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            dateItem.text = formatDate(item.date)
            tempMin.text = holder.itemView.context.getString(R.string.format_temp_min, item.tempMin)
            tempMax.text = holder.itemView.context.getString(R.string.format_temp_max, item.tempMax)
            downloadImage(holder.itemView.context, item.icon, weatherIcon)
        }
        when (getItemViewType(position)){
            FIRST_ITEM-> holder.binding.itemLayout.setBackgroundResource(R.drawable.top_rounded)
            LAST_ITEM-> holder.binding.itemLayout.setBackgroundResource(R.drawable.bot_rounded)
            else-> holder.binding.itemLayout.setBackgroundResource(R.color.secondaryColor)
        }
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