package com.example.prestaaula.ui.components

// Tarjeta reutilizable que muestra un equipo su estado y el boton para reservar si esta disponible
// La idea de estos comentarios es explicar el codigo como apunte de clase


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.prestaaula.ui.theme.PrestaAulaElevation
import com.example.prestaaula.model.Equipo
import com.example.prestaaula.model.EstadoEquipo

@Composable
// Se reutiliza por cada elemento del inventario El boton aparece solo si el equipo esta disponible
fun EquipoCard(equipo: Equipo, onReservar: (String) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = PrestaAulaElevation.Level1)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f).padding(16.dp)) {
                Text(equipo.nombre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(4.dp))
                Text(equipo.descripcion, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    StatusChip(equipo.estado)
                    if (equipo.estado == EstadoEquipo.DISPONIBLE) {
                        Button(onClick = { onReservar(equipo.id) }) { Text("Reservar") }
                    }
                }
            }
        }
    }
}

