package com.example.vynils.repository

import android.app.Application
import com.example.vynils.brokers.CacheManager
import com.example.vynils.brokers.NetworkServiceAdapter
import com.example.vynils.model.Collector

class CollectorRepository (private val application: Application){

    suspend fun refreshCollectorList(): List<Collector> {
        var resp = emptyList<Collector>()
        try{
            resp = NetworkServiceAdapter.getInstance(application).getCollectors()
            CacheManager.getInstance(application.applicationContext).addCollectors(resp)
        }catch (e: Exception) {
            resp = CacheManager.getInstance(application.applicationContext).getCollectors()
        }
        return resp
    }

    suspend fun refreshCollectorDetail(collectorId: Int): Collector {
        return NetworkServiceAdapter.getInstance(application).getCollector(collectorId)
    }
}