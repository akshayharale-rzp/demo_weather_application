package com.example.weatherapplication.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.weatherapplication.data.local.DailyForecastEntity
import java.time.LocalDate

@Composable
fun ForecastScreen(vm: ForecastViewModel = viewModel()) {
    val state by vm.state.collectAsState()
    var query by remember { mutableStateOf(TextFieldValue("")) }

    Column(Modifier.fillMaxSize().padding(24.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = query,
                onValueChange = { query = it },
                label = { Text("City") }
            )
            Spacer(Modifier.width(8.dp))
            Button(onClick = { if (query.text.isNotBlank()) vm.fetch(query.text.trim()) }) {
                Text("Fetch")
            }
        }
        Spacer(Modifier.height(16.dp))

        when (val s = state) {
            is ForecastUiState.Idle -> Text("Enter a city to see forecast")
            is ForecastUiState.Loading -> LinearProgressIndicator(Modifier.fillMaxWidth())
            is ForecastUiState.Success -> ForecastList(items = s.items)
            is ForecastUiState.Error -> {
                Text("Error: ${s.message}")
                if (s.cached.isNotEmpty()) {
                    Spacer(Modifier.height(8.dp))
                    Text("Showing cached data:")
                    ForecastList(items = s.cached)
                }
            }
        }
    }
}

@Composable
private fun ForecastList(items: List<DailyForecastEntity>) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(items) { item ->
            ForecastRow(item)
        }
    }
}

@Composable
private fun ForecastRow(item: DailyForecastEntity) {
    val date = LocalDate.ofEpochDay(item.dateEpochDay)
    Card(Modifier.fillMaxWidth()) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            if (!item.icon.isNullOrBlank()) {
                val url = "https://openweathermap.org/img/wn/${item.icon}@2x.png"
                Image(
                    painter = rememberAsyncImagePainter(url),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(date.toString(), style = MaterialTheme.typography.titleMedium)
                Text(item.condition, style = MaterialTheme.typography.bodyMedium)
            }
            Text(String.format("%.1f°C", item.avgTempCelsius), style = MaterialTheme.typography.titleMedium)
        }
    }
}


