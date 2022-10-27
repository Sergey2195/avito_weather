package com.example.avitoweather.data.localDataSource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.avitoweather.data.localDataSource.Dao.LocationDao
import com.example.avitoweather.data.localDataSource.entity.LocationDbEntity

@Database(entities = [LocationDbEntity::class], version = 1, exportSchema = false)
abstract class LocationDb: RoomDatabase() {
    abstract fun getLocationDao(): LocationDao
}