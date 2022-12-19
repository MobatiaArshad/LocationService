package com.example.locationservice.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Entity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): Dao

    companion object {
        fun getDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,"MyLocations"
            ).allowMainThreadQueries().build()
        }
    }
}