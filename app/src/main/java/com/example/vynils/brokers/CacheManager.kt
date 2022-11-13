package com.example.vynils.brokers

import android.content.Context
import androidx.collection.ArrayMap
import com.example.vynils.model.Album
import androidx.collection.arrayMapOf
import com.example.vynils.model.Performer

class CacheManager (context: Context) {
    companion object{
        var instance: CacheManager? = null
        fun getInstance(context: Context)=
            instance ?: synchronized(this) {
                instance ?: CacheManager(context).also {
                    instance = it
                }
            }
    }
    private var albums: ArrayMap<String, List<Album>> = arrayMapOf<String, List<Album>>()
    private val performers : ArrayMap<String, List<Performer>> = arrayMapOf<String, List<Performer>>()

    fun getAlbums(): List<Album> {
        return if (albums.containsKey("albumes")) albums["albumes"]!! else listOf<Album>()
    }

    fun addAlbums( album: List<Album>) {
        albums["albumes"] = album
    }

    fun addAlbum(album: Album){
        if(albums.containsKey("albumes")){
            val albumes = albums["albumes"]!!.toMutableList()
            for(i in 0 until albumes.size){
                if(albumes[i].id == album.id){
                    albumes[i] = album
                    albums["albumes"] = albumes
                    break
                }
            }
        }
    }

    fun getPerformers(): List<Performer>{
        return if (performers.containsKey("performers")) performers["performers"]!! else listOf<Performer>()
    }

}