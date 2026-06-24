package com.remka.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.remka.domain.Vehicle
import com.remka.domain.VehicleType
import java.util.UUID

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RemkaTheme {
                RemkaApp()
            }
        }
    }
}

@Composable
private fun RemkaTheme(content: @Composable () -> Unit) {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFFF8FAFC)
        ) {
            content()
        }
    }
}

@Composable
private fun RemkaApp() {
    val vehicles = remember {
        mutableStateListOf(
            Vehicle(
                id = "demo-motorcycle",
                type = VehicleType.MOTORCYCLE,
                name = "Мой мотоцикл",
                manufacturer = "Honda",
                model = "CB400",
                year = 2007,
                registrationNumber = "A123BC",
                currentMileage = 42000
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Remka",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A)
                )
                Text(
                    text = "Транспорт и история работ",
                    color = Color(0xFF64748B)
                )
            }

            Button(
                onClick = {
                    vehicles.add(
                        Vehicle(
                            id = UUID.randomUUID().toString(),
                            type = VehicleType.CAR,
                            name = "Новая машина",
                            manufacturer = "Toyota",
                            model = "Corolla",
                            year = 2012,
                            registrationNumber = null,
                            currentMileage = 180000
                        )
                    )
                }
            ) {
                Text("Добавить")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(vehicles, key = { vehicle -> vehicle.id }) { vehicle ->
                VehicleCard(vehicle = vehicle)
            }
        }
    }
}

@Composable
private fun VehicleCard(vehicle: Vehicle) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            VehicleIcon(type = vehicle.type)

            Column(
                modifier = Modifier
                    .padding(start = 14.dp)
                    .weight(1f)
            ) {
                Text(
                    text = vehicle.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF0F172A)
                )

                Text(
                    text = listOfNotNull(vehicle.manufacturer, vehicle.model, vehicle.year?.toString())
                        .joinToString(" ")
                        .ifBlank { "Модель не указана" },
                    color = Color(0xFF475569)
                )

                Text(
                    text = "Пробег: ${vehicle.currentMileage ?: "не указан"} км",
                    color = Color(0xFF64748B),
                    fontSize = 13.sp
                )
            }

            Text(
                text = vehicle.registrationNumber ?: "без номера",
                color = Color(0xFF334155),
                fontSize = 13.sp
            )
        }
    }
}

@Composable
private fun VehicleIcon(type: VehicleType) {
    val label = when (type) {
        VehicleType.MOTORCYCLE -> "M"
        VehicleType.CAR -> "A"
        VehicleType.SCOOTER -> "S"
        VehicleType.BICYCLE -> "B"
        VehicleType.ATV -> "Q"
        VehicleType.BOAT -> "L"
        VehicleType.OTHER -> "?"
    }

    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFE0F2FE)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0369A1)
        )
    }
}
