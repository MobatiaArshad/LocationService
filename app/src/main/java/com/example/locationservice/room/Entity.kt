package com.example.locationservice.room

import androidx.room.PrimaryKey

@androidx.room.Entity
data class Entity(
    @PrimaryKey val id: Int? = null,
    val lat: Double,
    val lng: Double,
    val time: String,
    val airplaneMode: Boolean,
)
