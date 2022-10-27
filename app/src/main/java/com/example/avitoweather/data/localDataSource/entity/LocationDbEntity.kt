package com.example.avitoweather.data.localDataSource.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Location_db")
data class LocationDbEntity(
    @PrimaryKey
    val timeAdded: Long,
    val label: String,
    val lon: String,
    val lat: String,
)