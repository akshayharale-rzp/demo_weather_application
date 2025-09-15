package com.example.weatherapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.weatherapplication.ui.theme.WeatherApplicationTheme
import com.example.weatherapplication.ui.ForecastScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherApplicationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ForecastScreen()
                }
            }
        }
    }
}