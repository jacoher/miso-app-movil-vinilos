package com.example.vynils.model

data class Collector(
    val id: Int,
    val name:String,
    val telephone:String,
    val email:String,
    val albums: List<AlbumSummary>?,
    val performers: List<Performer>?
)
