package com.example.prestaaula.ui.screens

// NOTAS DE AMARO Y SOTO
// Calcula indicadores desde los datos reales guardados en Room y presenta el historial más reciente.
// La idea de estos comentarios es explicar el código como apunte de clase.


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.prestaaula.model.Equipo
import com.example.prestaaula.model.EstadoEquipo
import com.example.prestaaula.model.Reserva
import com.example.prestaaula.ui.components.MetricCard
import com.example.prestaaula.ui.components.PrestaAulaTopBar
import com.example.prestaaula.ui.components.SectionHeader
import com.example.prestaaula.ui.theme.Fondo
import java.util.Calendar

@Composable
// Los indicadores se calculan usando las reservas y los equipos que están guardados.
fun ReportesScreen(equipos: List<Equipo>, reservas: List<Reserva>, onBack: () -> Unit) {
    // Obtenemos el mes actual para contar solo los préstamos de este mes.
    val ahora = Calendar.getInstance()
    val prestamosMes = reservas.count { reserva ->
        val fecha = Calendar.getInstance().apply { timeInMillis = reserva.creadaEn }
        fecha.get(Calendar.MONTH) == ahora.get(Calendar.MONTH) && fecha.get(Calendar.YEAR) == ahora.get(Calendar.YEAR)
    }
    val enMantencion = equipos.count { it.estado == EstadoEquipo.MANTENCION }
    // Agrupamos las devoluciones con problemas por nombre de equipo y ordenamos por cantidad.
    val porIncidencias = reservas
        .filter { it.estadoDevolucion != null && it.estadoDevolucion != "Buen estado" }
        .groupBy { it.equipoNombre }
        .mapValues { it.value.size }
        .entries
        .sortedByDescending { it.value }
    // Mostramos como máximo las seis reservas más recientes.
    val historial = reservas.sortedByDescending { it.creadaEn }.take(6)

    Scaffold(
        containerColor = Fondo,
        topBar = { PrestaAulaTopBar("Reportes", "UTP / Administración", onBack) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Spacer(Modifier.height(4.dp))
                Text("Resumen del CRA", style = MaterialTheme.typography.titleLarge)
                MetricCard(valor = prestamosMes.toString(), titulo = "PRÉSTAMOS REGISTRADOS ESTE MES")
                MetricCard(valor = enMantencion.toString(), titulo = "EQUIPOS EN MANTENCIÓN")
                MetricCard(valor = porIncidencias.sumOf { it.value }.toString(), titulo = "INCIDENCIAS REGISTRADAS")
                SectionHeader("EQUIPOS CON MÁS INCIDENCIAS")
                if (porIncidencias.isEmpty()) {
                    EmptyReportCard("No hay incidencias registradas en la base de datos.")
                }
            }
            items(porIncidencias, key = { "incidencia-${it.key}" }) { entrada ->
                ReportRow(titulo = entrada.key, detalle = "${entrada.value} incidencia(s) registrada(s)")
            }
            item {
                SectionHeader("HISTORIAL RECIENTE")
                if (historial.isEmpty()) EmptyReportCard("No hay actividad registrada todavía.")
            }
            items(historial, key = { "historial-${it.id}" }) { reserva ->
                ReportRow(
                    titulo = reserva.equipoNombre,
                    detalle = "${reserva.fecha} · ${reserva.horario} · ${reserva.estado}"
                )
            }
            item { Spacer(Modifier.height(16.dp)) }
        }
    }
}

@Composable
// Mensaje sencillo para cuando aún no hay información que mostrar.
private fun EmptyReportCard(message: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(message, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
// Fila reutilizable para cada incidencia o movimiento del historial.
private fun ReportRow(titulo: String, detalle: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(titulo, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
            Text(detalle, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
