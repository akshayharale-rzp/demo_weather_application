package com.example.weatherapplication.data.local

import android.content.Context
import androidx.room.Room

object DatabaseModule {
    fun provideDatabase(context: Context): AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "weather.db"
    ).fallbackToDestructiveMigration().build()
}


