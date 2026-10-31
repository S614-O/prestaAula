package com.example.prestaaula.ui.screens

// Esta pantalla muestra las listas que el ViewModel ya separo por fecha y estado
// Los botones mandan la accion de confirmar retiro

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.prestaaula.model.Reserva
import com.example.prestaaula.ui.components.OperationFeedback
import com.example.prestaaula.ui.components.PrestaAulaBottomBar
import com.example.prestaaula.ui.components.PrestaAulaTopBar
import com.example.prestaaula.ui.components.ReservaCard
import com.example.prestaaula.ui.components.SectionHeader

@Composable
fun MisReservasScreen(
    reservasHoy: List<Reserva>,
    reservasProximas: List<Reserva>,
    reservasDevueltas: List<Reserva>,
    cargando: Boolean,
    operacionEnCurso: Boolean,
    mensajeError: String?,
    mensajeExito: String?,
    onDismissMessage: () -> Unit,
    onBack: () -> Unit,
    onNavigate: (String) -> Unit,
    onConfirmarRetiro: (String) -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { PrestaAulaTopBar("Mis reservas", "Préstamos y horarios guardados", onBack) },
        bottomBar = { PrestaAulaBottomBar(selectedRoute = "mis_reservas", onNavigate = onNavigate) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (cargando || operacionEnCurso) item { LinearProgressIndicator() }
            if (mensajeError != null || mensajeExito != null) {
                item {
                    OperationFeedback(mensajeError, mensajeExito, onDismissMessage)
                }
            }
            item {
                Spacer(Modifier.height(4.dp))
                SectionHeader("HOY")
                if (reservasHoy.isEmpty()) {
                    Text("No tienes retiros programados para hoy.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            items(reservasHoy, key = { it.id }) { reserva ->
                ReservaCard(reserva, onConfirmarRetiro, botonDeshabilitado = operacionEnCurso)
            }
            item {
                SectionHeader("PRÓXIMAS")
                if (reservasProximas.isEmpty()) {
                    Text("No hay reservas pendientes para otras fechas.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            items(reservasProximas, key = { it.id }) { reserva ->
                ReservaCard(reserva, onConfirmarRetiro, botonDeshabilitado = operacionEnCurso)
            }
            if (reservasDevueltas.isNotEmpty()) {
                item { SectionHeader("DEVUELTAS") }
                items(reservasDevueltas, key = { "devuelta-${it.id}" }) { reserva ->
                    ReservaCard(reserva, onConfirmarRetiro, botonDeshabilitado = true)
                }
            }
            item { Spacer(Modifier.height(16.dp)) }
        }
    }
}
