package com.example.avitoweather.data.repository

import com.example.avitoweather.data.localDataSource.Dao.LocationDao
import com.example.avitoweather.data.localDataSource.entity.LocationDbEntity
import com.example.avitoweather.data.mapper.Mapper
import com.example.avitoweather.data.networkDataSource.NetworkDatasourceInterface
import com.example.avitoweather.data.networkDataSource.models.Location
import com.example.avitoweather.di.ApplicationScope
import com.example.avitoweather.domain.entites.LocationError
import com.example.avitoweather.domain.entites.LocationState
import com.example.avitoweather.domain.entites.LocationSuccess
import com.example.avitoweather.domain.interfaces.LocationRepositoryInterface
import com.example.avitoweather.domain.interfaces.WeatherRepositoryInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ApplicationScope
class LocationRepository @Inject constructor(
    private val networkDatasource: NetworkDatasourceInterface,
    private val locationDao: LocationDao,
    private val scope: CoroutineScope,
    private val mapper: Mapper,
) : LocationRepositoryInterface {

    //flow, true-loading in progress, false-loading finished
    override val isLoadingFlow: Flow<Boolean> = networkDatasource.isLoadingFlow

    //suspend function returning history from database
    override suspend fun getHistoryOfLocation(): List<LocationSuccess> {
        return mapper.transformListLocationDbEntityToLocationSuccess(
            locationDao.getAllFromDb().reversed()
        )
    }

    //deleting a location from history
    override suspend fun deleteElementOnHistory(label: String) {
        locationDao.deleteWithLabel(label)
    }

    //writing the location to the database if it is not the current location
    override fun addToDatabase(lat: String, lon: String, extra: Boolean, label: String) {
        scope.launch {
            if (label == LABEL_CURRENT_POSITION) {
                return@launch
            }
            val res = locationDao.findWithLabel(label)
            if (res != null) {
                locationDao.deleteWithLabel(label)
            }
            val locationDbEntity = LocationDbEntity(System.currentTimeMillis(), label, lon, lat)
            locationDao.addNewLocation(locationDbEntity)
        }
    }

    //if there is something in the database, then the last location is returned, otherwise the default location
    suspend fun getLastLocation(): Location {
        val lastElement = locationDao.getAllFromDb().lastOrNull()
        return if (lastElement != null) {
            Location(lastElement.lat, lastElement.lon)
        } else {
            Location()
        }
    }

    override suspend fun getLocation(requestStr: String): List<LocationState> {
        val result = networkDatasource.loadLocation(requestStr) ?: return listOf(LocationError)
        return mapper.transformListLocationElementToListLocationSuccess(result)
    }

    companion object {
        private const val LABEL_CURRENT_POSITION = "Current Position"
    }
}