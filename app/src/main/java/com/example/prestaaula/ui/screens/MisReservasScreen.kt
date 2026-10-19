package com.example.prestaaula.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.prestaaula.model.Reserva
import com.example.prestaaula.ui.components.PrestaAulaBottomBar
import com.example.prestaaula.ui.components.PrestaAulaTopBar
import com.example.prestaaula.ui.components.ReservaCard
import com.example.prestaaula.ui.components.SectionHeader
import com.example.prestaaula.ui.theme.Fondo

@Composable
fun MisReservasScreen(
    reservas: List<Reserva>,
    onBack: () -> Unit,
    onNavigate: (String) -> Unit,
    onConfirmarRetiro: (String) -> Unit
) {
    val hoy = reservas.filter { it.esHoy }
    val proximas = reservas.filterNot { it.esHoy }
    Scaffold(
        containerColor = Fondo,
        topBar = { PrestaAulaTopBar("Mis reservas", "Préstamos y horarios", onBack) },
        bottomBar = { PrestaAulaBottomBar(selectedRoute = "mis_reservas", onNavigate = onNavigate) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Spacer(Modifier.height(4.dp))
                SectionHeader("HOY")
                if (hoy.isEmpty()) Text("No tienes retiros programados para hoy.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            items(hoy, key = { it.id }) { reserva -> ReservaCard(reserva, onConfirmarRetiro) }
            item {
                SectionHeader("PRÓXIMAS")
                if (proximas.isEmpty()) Text("No hay reservas próximas.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            items(proximas, key = { it.id }) { reserva -> ReservaCard(reserva, onConfirmarRetiro) }
            item { Spacer(Modifier.height(16.dp)) }
        }
    }
}
