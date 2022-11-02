package com.example.vynils.repository

import android.app.Application
import com.example.vynils.model.Album
import com.example.vynils.services.WebService

class AlbumRepository (private val application: Application){

        suspend fun refreshAlbumList(): List<Album> {
            var resp = emptyList<Album>()
            try{
                resp = WebService.getInstance(application).getAlbums()
            }catch (e: Exception) {
                resp = CacheManager.getInstance(application.applicationContext).getAlbums()
            }
            return resp
        }
}