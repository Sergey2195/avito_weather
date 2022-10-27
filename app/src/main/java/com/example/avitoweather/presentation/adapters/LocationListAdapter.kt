package com.example.avitoweather.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import com.example.avitoweather.databinding.LocationItemBinding
import com.example.avitoweather.domain.entites.LocationSuccess
import com.example.avitoweather.presentation.diffUtils.LocationDiffCallback
import com.example.avitoweather.presentation.viewHolders.LocationViewHolder

class LocationListAdapter :
    ListAdapter<LocationSuccess, LocationViewHolder>(LocationDiffCallback()) {

    var itemClickListener: ((LocationSuccess) -> Unit)? = null
    var deleteItemClickListener: ((LocationSuccess) -> Unit)? = null
    var isHistory = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LocationItemBinding.inflate(inflater, parent, false)
        return LocationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        setupVisibility(holder)
        val item = getItem(position)
        setupClickListeners(holder, item)
        with(holder.binding) {
            labelTv.text = item.label
        }
    }

    private fun setupVisibility(holder: LocationViewHolder) {
        with(holder.binding) {
            isHistoryImageView.isVisible = isHistory
            deleteIcon.isVisible = isHistory
        }
    }

    private fun setupClickListeners(holder: LocationViewHolder, item: LocationSuccess) {
        holder.itemView.setOnClickListener {
            itemClickListener?.invoke(item)
        }
        holder.binding.deleteIcon.setOnClickListener {
            deleteItemClickListener?.invoke(item)
        }
    }
}