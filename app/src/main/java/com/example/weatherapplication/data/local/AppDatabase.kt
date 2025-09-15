package com.example.weatherapplication.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [DailyForecastEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun forecastDao(): ForecastDao
}


