package com.example.avitoweather.di

import com.example.avitoweather.data.networkDataSource.apiCall.WeatherApiCalls
import com.example.avitoweather.data.networkDataSource.interceptors.LoginInterceptor
import com.example.avitoweather.data.networkDataSource.wrappers.WeatherBaseUrlWrapper
import com.example.avitoweather.data.networkDataSource.wrappers.WeatherApiKeyWrapper
import com.example.avitoweather.di.qualifier.WeatherQualifier
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
interface WeatherNetworkModule {


    companion object{

        @ApplicationScope
        @Provides
        fun provideWeatherApiKeyWrapper(): WeatherApiKeyWrapper{
            return WeatherApiKeyWrapper("c3d99852-3260-4d95-8a21-26cd6fe95838")
        }

        @ApplicationScope
        @Provides
        fun provideBaseUrlWrapper():WeatherBaseUrlWrapper{
            return WeatherBaseUrlWrapper("https://api.weather.yandex.ru/v2/")
        }

        @ApplicationScope
        @Provides
        fun provideLoginInterceptor (
            weatherApiKeyWrapper: WeatherApiKeyWrapper
        ): LoginInterceptor {
            return LoginInterceptor(weatherApiKeyWrapper.apiKey)
        }

        @ApplicationScope
        @Provides
        fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
            return HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
        }

        @ApplicationScope
        @Provides
        fun provideOkHttpClient(
            loginInterceptor: LoginInterceptor,
            httpLoggingInterceptor: HttpLoggingInterceptor
        ):OkHttpClient{
            return OkHttpClient.Builder()
                .addInterceptor(loginInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .build()
        }

        @ApplicationScope
        @WeatherQualifier
        @Provides
        fun provideGsonConverterFactory(): GsonConverterFactory{
            return GsonConverterFactory.create()
        }


        @ApplicationScope
        @WeatherQualifier
        @Provides
        fun provideRetrofit(
            okHttpClient: OkHttpClient,
            @WeatherQualifier gsonConverterFactory: GsonConverterFactory,
            weatherBaseUrlWrapper: WeatherBaseUrlWrapper,
        ): Retrofit {
            return Retrofit
                .Builder()
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .baseUrl(weatherBaseUrlWrapper.baseUrl)
                .build()
        }

        @Provides
        @ApplicationScope
        fun provideApiCalls(
            @WeatherQualifier retrofit: Retrofit
        ): WeatherApiCalls {
            return retrofit.create(WeatherApiCalls::class.java)
        }
    }
}