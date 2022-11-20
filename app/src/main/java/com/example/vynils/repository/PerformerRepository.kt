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

    suspend fun refreshPerformerDetail(performerType: PerformerType, performerId: Int): Performer {
        try {
            var performer : Performer
            if(performerType == PerformerType.BAND){
                performer = NetworkServiceAdapter.getInstance(application).getBandsDetail(performerId)
            }
            else {
                performer = NetworkServiceAdapter.getInstance(application).getMusicianDetail(performerId)
            }

            return performer
        }catch (e: Exception) {
            return CacheManager.getInstance(application.applicationContext).getPerformer(performerType, performerId)
        }
    }

}