package com.example.prestaaula.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.prestaaula.model.DummyData
import com.example.prestaaula.ui.components.EquipoCard
import com.example.prestaaula.ui.components.PrestaAulaBottomBar
import com.example.prestaaula.ui.components.PrestaAulaTopBar
import com.example.prestaaula.ui.components.SectionHeader
import com.example.prestaaula.ui.theme.Fondo

@Composable
fun InventarioScreen(
    onBack: () -> Unit,
    onNavigate: (String) -> Unit,
    onReservarEquipo: (String) -> Unit
) {
    val notebooks = DummyData.equipos.filter { it.categoria == "NOTEBOOKS" }
    val proyectores = DummyData.equipos.filter { it.categoria == "PROYECTORES" }
    Scaffold(
        containerColor = Fondo,
        topBar = { PrestaAulaTopBar("Inventario", "Disponibilidad en tiempo real", onBack) },
        bottomBar = { PrestaAulaBottomBar(selectedRoute = "inventario", onNavigate = onNavigate) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Spacer(Modifier.height(4.dp))
                Text("Equipos del CRA", style = MaterialTheme.typography.titleLarge)
                Text("Selecciona un equipo disponible para reservarlo.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                SectionHeader("NOTEBOOKS")
            }
            items(notebooks, key = { it.id }) { equipo ->
                EquipoCard(equipo = equipo, onReservar = onReservarEquipo)
            }
            item { SectionHeader("PROYECTORES") }
            items(proyectores, key = { it.id }) { equipo ->
                EquipoCard(equipo = equipo, onReservar = onReservarEquipo)
            }
            item {
                Spacer(Modifier.height(4.dp))
                Button(
                    onClick = { onReservarEquipo((DummyData.equipos.firstOrNull { it.estado.name == "DISPONIBLE" } ?: DummyData.equipos.first()).id) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("+ Reservar un equipo")
                }
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}
