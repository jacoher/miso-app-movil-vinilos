package com.example.vynils.repository

import android.app.Application
import com.example.vynils.brokers.NetworkServiceAdapter
import com.example.vynils.model.Track

class TrackRepository (private val application: Application){
    suspend fun associateTrack(track: Track, albumId: Int) {
        NetworkServiceAdapter.getInstance(application).postTrack(track, albumId)
    }
}