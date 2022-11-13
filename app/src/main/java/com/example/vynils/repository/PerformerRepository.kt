package com.example.vynils.repository

import android.app.Application
import android.widget.Toast
import com.example.vynils.brokers.CacheManager
import com.example.vynils.brokers.NetworkServiceAdapter
import com.example.vynils.model.Performer
import com.example.vynils.model.PerformerType

class PerformerRepository (private val application: Application){

    suspend fun refreshPerformerList(): List<Performer> {
        try {
            val musicians = NetworkServiceAdapter.getInstance(application).getMusicians()
            val bands = NetworkServiceAdapter.getInstance(application).getBands()
            return musicians.plus(bands)
        }catch (e: Exception) {
            return CacheManager.getInstance(application.applicationContext).getPerformers()
        }
    }

}