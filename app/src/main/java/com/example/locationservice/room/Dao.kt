package com.example.locationservice.room

import androidx.room.Insert
import androidx.room.Query

@androidx.room.Dao
interface Dao {

    @Query("SELECT * FROM ENTITY")
    fun getAllLoc(): List<Entity>

    @Insert
    fun insertLocation(vararg data: Entity)

    @Query("DELETE FROM ENTITY")
    fun deleteAllData()
}