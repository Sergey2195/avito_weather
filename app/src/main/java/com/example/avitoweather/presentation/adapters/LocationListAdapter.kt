package com.example.avitoweather.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.avitoweather.databinding.LocationItemBinding
import com.example.avitoweather.domain.entites.LocationSuccess
import com.example.avitoweather.presentation.diffUtils.LocationDiffCallback
import com.example.avitoweather.presentation.viewHolders.LocationViewHolder

class LocationListAdapter: ListAdapter<LocationSuccess, LocationViewHolder>(LocationDiffCallback()) {

    var itemClickListener: ((LocationSuccess) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LocationItemBinding.inflate(inflater, parent, false)
        return LocationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            itemClickListener?.invoke(item)
        }
        with(holder.binding){
            labelTv.text = item.label
        }
    }
}