package com.example.weatherapplication.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {
    // 5 day / 3 hour forecast endpoint. We'll aggregate the first 3 days.
    @GET("data/2.5/forecast")
    suspend fun getForecast(
        @Query("q") city: String,
        @Query("cnt") cnt: Int = 40,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): ForecastResponse
}


