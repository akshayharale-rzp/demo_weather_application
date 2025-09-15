package com.example.weatherapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ForecastDao {
    @Query("SELECT * FROM daily_forecast WHERE cityKey = :cityKey ORDER BY dateEpochDay ASC")
    suspend fun getForecastForCity(cityKey: String): List<DailyForecastEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(items: List<DailyForecastEntity>)

    @Query("DELETE FROM daily_forecast WHERE cityKey = :cityKey")
    suspend fun deleteForCity(cityKey: String)
}


