package com.example.prestaaula.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.example.prestaaula.model.DummyData
import com.example.prestaaula.model.Reporte
import com.example.prestaaula.ui.components.MetricCard
import com.example.prestaaula.ui.components.PrestaAulaTopBar
import com.example.prestaaula.ui.components.SectionHeader
import com.example.prestaaula.ui.theme.Fondo

@Composable
fun ReportesScreen(onBack: () -> Unit) {
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
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    MetricCard("128", "PRÉSTAMOS ESTE MES", Modifier.weight(1f))
                    MetricCard("4", "EQUIPOS EN MANTENCIÓN", Modifier.weight(1f))
                }
                SectionHeader("EQUIPOS CON MÁS INCIDENCIAS")
            }
            items(DummyData.equiposConIncidencias, key = { it.titulo }) { reporte -> ReporteCard(reporte) }
            item { SectionHeader("HISTORIAL RECIENTE") }
            items(DummyData.historialReciente, key = { it.titulo + it.fecha }) { reporte -> ReporteCard(reporte) }
            item { Spacer(Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun ReporteCard(reporte: Reporte) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(reporte.titulo, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text(reporte.detalle, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(6.dp))
            Text(reporte.fecha, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
