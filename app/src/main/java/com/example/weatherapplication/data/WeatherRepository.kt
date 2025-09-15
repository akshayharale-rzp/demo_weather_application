package com.example.weatherapplication.data

import android.content.Context
import androidx.room.withTransaction
import com.example.weatherapplication.data.local.AppDatabase
import com.example.weatherapplication.data.local.DailyForecastEntity
import com.example.weatherapplication.data.local.DatabaseModule
import com.example.weatherapplication.data.remote.NetworkModule
import com.example.weatherapplication.data.remote.OpenWeatherService

class WeatherRepository private constructor(context: Context) {
    private val db: AppDatabase = DatabaseModule.provideDatabase(context)
    private val api: OpenWeatherService = NetworkModule.provideOpenWeatherService()
    private val dao = db.forecastDao()

    suspend fun loadThreeDayForecast(city: String, apiKey: String): List<DailyForecastEntity> {
        return try {
            val response = api.getForecast(city = city, apiKey = apiKey)
            val aggregated = ForecastMapper.aggregateToThreeDays(cityMeta = response.city, items = response.items)
            db.withTransaction {
                dao.deleteForCity(ForecastMapper.normalizeCityKey(response.city.name))
                dao.upsertAll(aggregated)
            }
            aggregated
        } catch (e: Exception) {
            dao.getForecastForCity(ForecastMapper.normalizeCityKey(city))
        }
    }

    suspend fun readCachedForecast(city: String): List<DailyForecastEntity> =
        dao.getForecastForCity(ForecastMapper.normalizeCityKey(city))

    companion object {
        @Volatile private var INSTANCE: WeatherRepository? = null
        fun get(context: Context): WeatherRepository = INSTANCE ?: synchronized(this) {
            INSTANCE ?: WeatherRepository(context).also { INSTANCE = it }
        }
    }
}


