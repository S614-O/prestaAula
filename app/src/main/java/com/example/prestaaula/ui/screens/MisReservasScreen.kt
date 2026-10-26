package com.example.prestaaula.ui.screens

// NOTAS DE AMARO Y SOTO
// Organiza las reservas entre las de hoy, las próximas y las que ya fueron devueltas.
// La idea de estos comentarios es explicar el código como apunte de clase.


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
// Divide las reservas según su fecha y si ya fueron devueltas.
fun MisReservasScreen(
    reservas: List<Reserva>,
    onBack: () -> Unit,
    onNavigate: (String) -> Unit,
    onConfirmarRetiro: (String) -> Unit
) {
    // Estas listas se calculan desde los datos que llegan de Room, no son datos de ejemplo.
    val hoy = reservas.filter { it.esHoy && it.estado != "DEVUELTA" }
    val proximas = reservas.filter { !it.esHoy && it.estado != "DEVUELTA" }
    val completadas = reservas.filter { it.estado == "DEVUELTA" }
// La barra inferior permite cambiar a Inventario sin volver a crear una pila de pantallas.
    Scaffold(
        containerColor = Fondo,
        topBar = { PrestaAulaTopBar("Mis reservas", "Préstamos y horarios guardados", onBack) },
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
                if (proximas.isEmpty()) Text("No hay reservas pendientes para otras fechas.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            items(proximas, key = { it.id }) { reserva -> ReservaCard(reserva, onConfirmarRetiro) }
            if (completadas.isNotEmpty()) {
                item { SectionHeader("DEVUELTAS") }
                items(completadas, key = { "devuelta-${it.id}" }) { reserva -> ReservaCard(reserva, onConfirmarRetiro) }
            }
            item { Spacer(Modifier.height(16.dp)) }
        }
    }
}
