package com.example.weatherapplication.data.remote

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("list") val items: List<ForecastItem>,
    @SerializedName("city") val city: City
)

data class City(
    @SerializedName("name") val name: String,
    @SerializedName("country") val country: String?,
    @SerializedName("timezone") val timezoneSeconds: Int? = null
)

data class ForecastItem(
    @SerializedName("dt") val timestampSeconds: Long,
    @SerializedName("main") val main: MainInfo,
    @SerializedName("weather") val weather: List<WeatherInfo>
)

data class MainInfo(
    @SerializedName("temp") val tempKelvin: Double
)

data class WeatherInfo(
    @SerializedName("main") val condition: String,
    @SerializedName("description") val description: String?,
    @SerializedName("icon") val icon: String?
)


