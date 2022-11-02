package com.example.vynils.repository

import android.content.Context
import androidx.collection.ArrayMap
import androidx.collection.arrayMapOf
import com.example.vynils.model.Album

class CacheManager (context: Context) {
    companion object {
        var instance: CacheManager? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CacheManager(context).also {
                    instance = it
                }
            }
    }

    private var albums: ArrayMap<String, List<Album>> = arrayMapOf<String, List<Album>>()

    fun getAlbums(): List<Album> {
        return if (albums.containsKey("albumes")) albums["albumes"]!! else listOf<Album>()
    }
}