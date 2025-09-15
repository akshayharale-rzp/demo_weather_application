package com.example.weatherapplication.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "daily_forecast",
    indices = [Index(value = ["cityKey", "dateEpochDay"], unique = true)]
)
data class DailyForecastEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val cityKey: String,
    val displayCity: String,
    val dateEpochDay: Long,
    val avgTempCelsius: Double,
    val condition: String,
    val icon: String?
)


