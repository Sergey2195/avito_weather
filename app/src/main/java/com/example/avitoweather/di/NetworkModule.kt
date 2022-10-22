package com.example.avitoweather.di

import com.example.avitoweather.data.networkDataSource.apiCall.ApiCalls
import com.example.avitoweather.data.networkDataSource.interceptors.LoginInterceptor
import com.example.avitoweather.data.networkDataSource.wrappers.BaseUrlWrapper
import com.example.avitoweather.data.networkDataSource.wrappers.WeatherApiKeyWrapper
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
interface NetworkModule {


    companion object{

        @Provides
        fun provideWeatherApiKeyWrapper(): WeatherApiKeyWrapper{
            return WeatherApiKeyWrapper("c3d99852-3260-4d95-8a21-26cd6fe95838")
        }

        @Provides
        fun provideBaseUrlWrapper():BaseUrlWrapper{
            return BaseUrlWrapper("https://api.weather.yandex.ru/v2/")
        }

        @Provides
        fun provideLoginInterceptor (
            weatherApiKeyWrapper: WeatherApiKeyWrapper
        ): LoginInterceptor {
            return LoginInterceptor(weatherApiKeyWrapper.apiKey)
        }

        @Provides
        fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
            return HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
        }

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

        @Provides
        fun provideGsonConverterFactory(): GsonConverterFactory{
            return GsonConverterFactory.create()
        }


        @Provides
        fun provideRetrofit(
            okHttpClient: OkHttpClient,
            gsonConverterFactory: GsonConverterFactory,
            baseUrlWrapper: BaseUrlWrapper,
        ): Retrofit {
            return Retrofit
                .Builder()
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .baseUrl(baseUrlWrapper.baseUrl)
                .build()
        }

        @Provides
        @ApplicationScope
        fun provideApiCalls(retrofit: Retrofit): ApiCalls {
            return retrofit.create(ApiCalls::class.java)
        }
    }
}