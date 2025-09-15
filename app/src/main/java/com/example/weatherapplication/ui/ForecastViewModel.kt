package com.example.weatherapplication.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapplication.BuildConfig
import com.example.weatherapplication.data.WeatherRepository
import com.example.weatherapplication.data.local.DailyForecastEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface ForecastUiState {
    data object Idle : ForecastUiState
    data object Loading : ForecastUiState
    data class Success(val city: String, val items: List<DailyForecastEntity>) : ForecastUiState
    data class Error(val message: String, val cached: List<DailyForecastEntity>) : ForecastUiState
}

class ForecastViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = WeatherRepository.get(app)

    private val _state = MutableStateFlow<ForecastUiState>(ForecastUiState.Idle)
    val state: StateFlow<ForecastUiState> = _state

    fun fetch(city: String) {
        viewModelScope.launch {
            _state.value = ForecastUiState.Loading
            val apiKey = BuildConfig.OWM_API_KEY
            try {
                val items = repository.loadThreeDayForecast(city, apiKey)
                _state.value = ForecastUiState.Success(city = city, items = items)
            } catch (e: Exception) {
                val cached = repository.readCachedForecast(city)
                _state.value = ForecastUiState.Error(message = e.message ?: "Error", cached = cached)
            }
        }
    }
}


