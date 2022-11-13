package com.example.vynils.model

// artista
data class Performer (
    val id: String, // BAND_101, MUSICIAN_100,..
    val performerId: Int,
    val name:String,
    val image:String,
    val description:String,
    val date: String,
    val performerType: Enum<PerformerType>,
    val albums : List<Album>?
    )
