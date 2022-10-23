package com.example.avitoweather.presentation.utils

import android.content.Context
import android.widget.ImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.avitoweather.R

object Utils {
    fun downloadImage(context: Context, imageSubUrl: String, icon: ImageView){
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

    fun formatDate(res: String): String{
        val dayStartIndex = res.indexOfLast { it == '-' }
        val day = res.substring(dayStartIndex+1,res.length)
        val monthStartIndex = res.indexOfFirst { it == '-' }
        val month = res.substring(monthStartIndex+1, dayStartIndex)
        return "$day.$month"
    }

    fun formatTemp(temp: Int): String{
        return if (temp > 0){
            "+$temp°"
        }else{
            "$temp°"
        }
    }
}