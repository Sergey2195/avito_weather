package com.example.avitoweather.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.avitoweather.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay

//utils
object Utils {

    //fun to download weather icons
    fun downloadImage(context: Context, imageSubUrl: String, icon: ImageView) {
        val string = context.getString(R.string.icon_url_string_format, imageSubUrl)
        val imageLoader = ImageLoader.Builder(context)
            .components {
                add(SvgDecoder.Factory())
            }
            .build()
        val request = ImageRequest.Builder(context)
            .data(string)
            .target(icon)
            .build()
        imageLoader.enqueue(request)
    }

    fun formatDate(res: String): String {
        val dayStartIndex = res.indexOfLast { it == '-' }
        val day = res.substring(dayStartIndex + 1, res.length)
        val monthStartIndex = res.indexOfFirst { it == '-' }
        val month = res.substring(monthStartIndex + 1, dayStartIndex)
        return "$day.$month"
    }

    fun formatTemp(temp: Int): String {
        return if (temp > 0) {
            "+$temp°"
        } else {
            "$temp°"
        }
    }

    //find current position
    suspend fun getLocation(context: Context, activity: Activity): List<String> {
        val fusedLocationProviderClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(context)
        val task = fusedLocationProviderClient.lastLocation
        if (ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
            return emptyList()
        }
        while (true){
            if (task.isComplete) {
                return listOf(task.result.latitude.toString(), task.result.longitude.toString())
            }else{
                delay(50)
            }
        }
    }
}