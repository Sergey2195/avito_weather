package com.example.avitoweather.di.modules

import android.app.Application
import androidx.room.Room
import com.example.avitoweather.data.localDataSource.Dao.LocationDao
import com.example.avitoweather.data.localDataSource.LocationDb
import com.example.avitoweather.di.ApplicationScope
import dagger.Module
import dagger.Provides


@Module
interface LocationDatabaseModule {
    companion object {
        @ApplicationScope
        @Provides
        fun provideLocationDatabase(
            application: Application
        ): LocationDb {
            return Room.databaseBuilder(
                application.applicationContext,
                LocationDb::class.java,
                "location_db"
            ).build()
        }

        @Provides
        fun provideDao(locationDb: LocationDb): LocationDao {
            return locationDb.getLocationDao()
        }
    }
}