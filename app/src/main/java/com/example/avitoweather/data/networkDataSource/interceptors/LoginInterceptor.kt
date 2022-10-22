package com.example.avitoweather.data.networkDataSource.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class LoginInterceptor @Inject constructor(
    private val apiKeyWeather: String
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("X-Yandex-API-Key", apiKeyWeather)
            .build()
        return chain.proceed(request)
    }
}