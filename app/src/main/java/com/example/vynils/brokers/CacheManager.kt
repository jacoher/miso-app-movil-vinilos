package com.example.vynils.brokers

import android.content.Context
import androidx.collection.ArrayMap
import com.example.vynils.model.Album
import androidx.collection.arrayMapOf
import com.example.vynils.model.Collector
import com.example.vynils.model.PerformerType
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
    private val collectors : ArrayMap<String, List<Collector>> = arrayMapOf<String, List<Collector>>()

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

    fun getPerformer(performerType: PerformerType, performerId: Int): Performer{
        if(performers.containsKey("performers")){
            val performers: List<Performer> = performers["performers"]!!
            for(i in 0 until performers.size){
                if(performers[i].performerId == performerId && performers[i].performerType == performerType){
                    return performers[i]
                }
            }
        }
        return Performer(
            id = "", performerId = 0 , name = "", image = "", description = "", date = "", performerType = PerformerType.MUSICIAN, albums = listOf<Album>()
        )
    }

    fun getAlbum(albumId: Int): Album {
        if(albums.containsKey("albumes")){
            val albumes: List<Album> = albums["albumes"]!!
            for(i in 0 until albumes.size){
                if(albumes[i].id == albumId){
                    return albumes[i]
                }
            }
        }
        return Album(id = 0, name = "", cover = "", releaseDate = "", description = "", genre = "",recordLabel = "", tracks = emptyList(), performers = emptyList())
    }

    fun getCollectors(): List<Collector> {
        return if (collectors.containsKey("collectors")) collectors["collectors"]!! else listOf<Collector>()
    }

}