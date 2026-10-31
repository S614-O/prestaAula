package com.example.prestaaula.ui.screens

// Esta pantalla recibe los reportes ya calculados desde el ViewModel
// Aqui solamente mostramos los numeros y las listas

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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.prestaaula.ui.components.MetricCard
import com.example.prestaaula.ui.components.OperationFeedback
import com.example.prestaaula.ui.components.PrestaAulaTopBar
import com.example.prestaaula.ui.components.SectionHeader
import com.example.prestaaula.ui.theme.PrestaAulaElevation
import com.example.prestaaula.ui.viewmodel.ReportesUiState

@Composable
fun ReportesScreen(
    reportes: ReportesUiState,
    cargando: Boolean,
    mensajeError: String?,
    onDismissMessage: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { PrestaAulaTopBar("Reportes", "UTP / Administración", onBack) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (mensajeError != null) {
                item { OperationFeedback(mensajeError, null, onDismissMessage) }
            }
            if (cargando) {
                item { LinearProgressIndicator(modifier = Modifier.fillMaxWidth()) }
            }
            item {
                Spacer(Modifier.height(4.dp))
                Text("Resumen del CRA", style = MaterialTheme.typography.titleLarge)
                MetricCard(valor = reportes.prestamosMes.toString(), titulo = "PRÉSTAMOS REGISTRADOS ESTE MES")
                MetricCard(valor = reportes.equiposEnMantencion.toString(), titulo = "EQUIPOS EN MANTENCIÓN")
                MetricCard(valor = reportes.totalIncidencias.toString(), titulo = "INCIDENCIAS REGISTRADAS")
                SectionHeader("EQUIPOS CON MÁS INCIDENCIAS")
                if (reportes.equiposConIncidencias.isEmpty()) {
                    EmptyReportCard("No hay incidencias registradas en la base de datos.")
                }
            }
            items(reportes.equiposConIncidencias, key = { "incidencia-${it.equipoNombre}" }) { incidencia ->
                ReportRow(
                    titulo = incidencia.equipoNombre,
                    detalle = "${incidencia.cantidad} incidencia(s) registrada(s)"
                )
            }
            item {
                SectionHeader("HISTORIAL RECIENTE")
                if (reportes.historialReciente.isEmpty()) {
                    EmptyReportCard("No hay actividad registrada todavía.")
                }
            }
            items(reportes.historialReciente, key = { "historial-${it.id}" }) { reserva ->
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
private fun EmptyReportCard(message: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = PrestaAulaElevation.Level1)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(message, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
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
