package com.example.prestaaula.ui.components

// NOTAS DE AMARO Y SOTO
// Tarjeta para mostrar una reserva y permitir confirmar el retiro cuando todavía está reservada.
// La idea de estos comentarios es explicar el código como apunte de clase.


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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.prestaaula.model.Reserva

@Composable
// Muestra datos principales de una reserva y el botón que corresponde a su estado.
fun ReservaCard(reserva: Reserva, onConfirmarRetiro: (String) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(reserva.equipoNombre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(reserva.estado, color = if (reserva.estado == "RETIRADA") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(6.dp))
            Text("${reserva.fecha} · ${reserva.horario}", style = MaterialTheme.typography.bodyMedium)
            Text("${reserva.curso} · ${reserva.docente}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            // Solo una reserva pendiente puede confirmar el retiro.
            if (reserva.estado == "RESERVADA") {
                Spacer(Modifier.height(12.dp))
                Button(onClick = { onConfirmarRetiro(reserva.id) }, modifier = Modifier.fillMaxWidth()) {
                    Text("Confirmar retiro")
                }
            // Si ya se retiró, mostramos el aviso y desactivamos el botón.
            } else if (reserva.estado == "RETIRADA") {
                Spacer(Modifier.height(12.dp))
                OutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth(), enabled = false) {
                    Text("Retiro confirmado · pendiente de devolución")
                }
            }
        }
    }
}
