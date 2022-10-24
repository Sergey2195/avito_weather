package com.example.avitoweather.di

import com.example.avitoweather.data.networkDataSource.apiCall.LocationApiCalls
import com.example.avitoweather.data.networkDataSource.wrappers.LocationApiKeyWrapper
import com.example.avitoweather.data.networkDataSource.wrappers.LocationBaseUrlWrapper
import com.example.avitoweather.di.qualifier.LocationQualifier
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
interface LocationNetworkModule {
    companion object {
        @ApplicationScope
        @Provides
        fun provideBaseUrlLocation(): LocationBaseUrlWrapper {
            return LocationBaseUrlWrapper("http://api.positionstack.com/v1/")
        }

        @ApplicationScope
        @Provides
        fun provideApiKeyLocation(): LocationApiKeyWrapper {
            return LocationApiKeyWrapper("933fb107d069cf5d7cea4f6380e0d32b")
        }

        @ApplicationScope
        @LocationQualifier
        @Provides
        fun provideGsonConverter(): GsonConverterFactory {
            return GsonConverterFactory.create()
        }

        @ApplicationScope
        @LocationQualifier
        @Provides
        fun provideOkHttpClient(): OkHttpClient {
            val interceptor =
                HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
            return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
        }


        @ApplicationScope
        @Provides
        @LocationQualifier
        fun provideRetrofit(
            @LocationQualifier httpClient: OkHttpClient,
            @LocationQualifier gsonConverterFactory: GsonConverterFactory,
            baseUrlWrapper: LocationBaseUrlWrapper
        ): Retrofit {
            return Retrofit.Builder()
                .client(httpClient)
                .addConverterFactory(gsonConverterFactory)
                .baseUrl(baseUrlWrapper.baseUrl)
                .build()
        }

        @ApplicationScope
        @Provides
        fun provideApiCalls(
            @LocationQualifier retrofit: Retrofit
        ): LocationApiCalls {
            return retrofit.create(LocationApiCalls::class.java)
        }
    }
}