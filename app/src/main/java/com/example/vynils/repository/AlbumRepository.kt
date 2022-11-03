package com.example.vynils.repository

import android.app.Application
import android.widget.Toast
import com.example.vynils.model.Album
import com.example.vynils.brokers.CacheManager
import com.example.vynils.brokers.VolleyBroker

class AlbumRepository (private val application: Application){

    suspend fun refreshAlbumList(): List<Album> {
        var resp = emptyList<Album>()
        try{
            resp = VolleyBroker.getInstance(application).getAlbums()
        }catch (e: Exception) {
            resp = CacheManager.getInstance(application.applicationContext).getAlbums()
            Toast.makeText(application.applicationContext, "Sin conexión, datos del cache", Toast.LENGTH_LONG).show()
        }
        return resp
    }

}