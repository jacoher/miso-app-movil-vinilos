package com.example.vynils.brokers

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.vynils.model.Album
import com.example.vynils.model.Performer
import com.example.vynils.model.PerformerType
import com.example.vynils.model.Track
import org.json.JSONArray
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class VolleyBroker constructor(context: Context) {
    companion object{
        const val BASE_URL= "https://vynils-back-miso.herokuapp.com/"
        var instance: VolleyBroker? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: VolleyBroker(context).also {
                    instance = it
                }
            }
    }
    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }
    suspend fun getAlbums() = suspendCoroutine<List<Album>>{ cont->
        requestQueue.add(getRequest("albums",
            Response.Listener<String> { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Album>()

                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)

                    val performers = item.getJSONArray("performers")
                    val performerList = mutableListOf<Performer>()
                    for (j in 0 until item.getJSONArray("performers").length()){
                        performerList.add(
                            j,
                            Performer(
                                id = PerformerType.MUSICIAN.toString() + "_" + performers.getJSONObject(j).getInt("id"),
                                performerId = performers.getJSONObject(j).getInt("id"),
                                name = performers.getJSONObject(j).getString("name"),
                                image = "",
                                date = "",
                                description = "",
                                performerType = PerformerType.MUSICIAN,
                                albums = mutableListOf<Album>()
                            )
                        )
                    }

                    val tracks = item.getJSONArray("tracks")
                    val tracksList = mutableListOf<Track>()
                    for (j in 0 until item.getJSONArray("tracks").length()){
                        tracksList.add(
                            j,
                            Track(
                                id = tracks.getJSONObject(j).getInt("id"),
                                name = tracks.getJSONObject(j).getString("name"),
                                duration = tracks.getJSONObject(j).getString("duration")
                            )
                        )
                    }

                    list.add(
                        i,
                        Album(
                            id = item.getInt("id"),
                            name = item.getString("name"),
                            cover = item.getString("cover"),
                            recordLabel = item.getString("recordLabel"),
                            releaseDate = item.getString("releaseDate"),
                            genre = item.getString("genre"),
                            description = item.getString("description"),
                            performers = performerList,
                            tracks = tracksList
                        )
                    )
                }
                cont.resume(list)
            },
            Response.ErrorListener {
                cont.resumeWithException(it)
            }))
    }

    private fun getRequest(
        path: String,
        responseListener: Response.Listener<String>,
        errorListener: Response.ErrorListener
    ): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL + path, responseListener, errorListener)
    }

}