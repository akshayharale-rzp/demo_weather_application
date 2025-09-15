package com.example.weatherapplication.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherapplication.data.local.DailyForecastEntity
import com.example.weatherapplication.data.remote.City
import com.example.weatherapplication.data.remote.ForecastItem
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

object ForecastMapper {
    fun aggregateToThreeDays(
        cityMeta: City,
        items: List<ForecastItem>
    ): List<DailyForecastEntity> {
        val offsetSeconds = cityMeta.timezoneSeconds ?: 0
        val zoneOffset = ZoneOffset.ofTotalSeconds(offsetSeconds)
        val cityKey = normalizeCityKey(cityMeta.name)

        val byDay = items.groupBy { item ->
            LocalDate.ofInstant(Instant.ofEpochSecond(item.timestampSeconds), zoneOffset)
        }

        val sortedDays = byDay.toSortedMap().entries.toList()
        val firstThree = sortedDays.take(3)

        return firstThree.map { (date, dayItems) ->
                val avgTemp = dayItems.map { it.main.tempKelvin }.average()
                val representative = dayItems.firstOrNull()?.weather?.firstOrNull()
                DailyForecastEntity(
                    cityKey = cityKey,
                    displayCity = cityMeta.name,
                    dateEpochDay = date.toEpochDay(),
                    avgTempCelsius = avgTemp,
                    condition = representative?.condition ?: "",
                    icon = representative?.icon
                )
            }
    }

    fun normalizeCityKey(input: String): String = input.trim().lowercase()
}


