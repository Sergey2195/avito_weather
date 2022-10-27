package com.example.avitoweather.data.localDataSource.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.avitoweather.data.localDataSource.entity.LocationDbEntity

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewLocation(location: LocationDbEntity)

    @Query("SELECT * FROM location_db ORDER BY timeAdded")
    suspend fun getAllFromDb(): List<LocationDbEntity>

    @Query("DELETE FROM location_db")
    suspend fun deleteAll()

    @Query("DELETE FROM location_db WHERE label == :label")
    suspend fun deleteWithLabel(label: String)

    @Query("SELECT * FROM location_db WHERE label == :label LIMIT 1")
    suspend fun findWithLabel(label: String): LocationDbEntity?
}