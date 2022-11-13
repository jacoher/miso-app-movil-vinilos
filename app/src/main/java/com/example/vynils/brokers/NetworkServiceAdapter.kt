package com.example.vynils.brokers

import android.content.Context
import android.util.ArrayMap
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.vynils.model.Album
import com.example.vynils.model.Performer
import com.example.vynils.model.PerformerType
import com.example.vynils.model.Track
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class NetworkServiceAdapter constructor(context: Context) {
    companion object{
        const val BASE_URL= "https://vynils-back-miso.herokuapp.com/"
        var instance: NetworkServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: NetworkServiceAdapter(context).also {
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

    suspend fun postAlbum(album: Album){
        val url = BASE_URL + "albums"

        val jsonObjectAlbum = JSONObject()
        jsonObjectAlbum.put("name",album.name)
        jsonObjectAlbum.put("cover",album.cover)
        jsonObjectAlbum.put("releaseDate",album.releaseDate)
        jsonObjectAlbum.put("description",album.description)
        jsonObjectAlbum.put("genre",album.genre)
        jsonObjectAlbum.put("recordLabel",album.recordLabel)

        requestQueue.add(object : JsonObjectRequest(
            Method.POST, url, jsonObjectAlbum,
            object : Response.Listener<JSONObject?> {
                override fun onResponse(response: JSONObject?) {
                    Log.i("StartActivity", response.toString())
                }
            }, object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError) {
                    Log.i("StartActivity", error.toString())
                }
            }) {

            override fun getHeaders(): Map<String, String> {
                Log.d("parametros post",body.toString())
                val headers = ArrayMap<String, String>()
                headers["Content-Type"] = "application/json"
                return headers
            }

//            override fun getParams(): Map<String, String> {
//                val params: MutableMap<String, String> = ArrayMap()
//                return params
//            }
        })
    }


    suspend fun getMusicians() = suspendCoroutine<List<Performer>>{ cont->
        requestQueue.add(getRequest("musicians",
            Response.Listener<String> { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Performer>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(
                        i,
                        Performer(
                            id = PerformerType.MUSICIAN.toString() + "_" + item.getInt("id"),
                            performerId = item.getInt("id"),
                            name = item.getString("name"),
                            image = item.getString("image"),
                            description = item.getString("description"),
                            date = item.getString("birthDate"),
                            performerType = PerformerType.MUSICIAN,
                            albums = listOf<Album>()
                        )
                    )
                }
                cont.resume(list)
            },
            Response.ErrorListener {
                cont.resumeWithException(it)
            }))
    }

    suspend fun getBands() = suspendCoroutine<List<Performer>>{ cont->
        requestQueue.add(getRequest("bands",
            Response.Listener<String> { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Performer>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(
                        i,
                        Performer(
                            id = PerformerType.BAND.toString() +"_" + item.getInt("id"),
                            performerId = item.getInt("id"),
                            name = item.getString("name"),
                            image = item.getString("image"),
                            description = item.getString("description"),
                            date = item.getString("creationDate"),
                            performerType = PerformerType.BAND,
                            albums = listOf<Album>()
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