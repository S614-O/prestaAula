package com.example.prestaaula.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.prestaaula.ui.components.PrestaAulaTopBar
import com.example.prestaaula.ui.theme.Fondo

private val estadosDevolucion = listOf("Buen estado", "Con observaciones", "Dañado", "Incompleto")

@Composable
fun RegistrarDevolucionScreen(onBack: () -> Unit) {
    var estado by remember { mutableStateOf(estadosDevolucion.first()) }
    var observaciones by remember { mutableStateOf("") }
    var desplegado by remember { mutableStateOf(false) }
    var confirmado by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Fondo,
        topBar = { PrestaAulaTopBar("Registrar devolución", "Encargado(a) CRA", onBack) }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).verticalScroll(rememberScrollState()).padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Equipo pendiente de devolución", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("Notebook #07", style = MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.height(5.dp))
                    Text("Prof. Gonzalez · 3°B", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(Modifier.height(5.dp))
                    Text("Retiro registrado: hoy, 08:00", style = MaterialTheme.typography.bodySmall)
                }
            }
            Text("Estado al devolver", style = MaterialTheme.typography.labelLarge)
            Box {
                OutlinedButton(onClick = { desplegado = true }, modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(estado)
                        Text("⌄")
                    }
                }
                DropdownMenu(expanded = desplegado, onDismissRequest = { desplegado = false }) {
                    estadosDevolucion.forEach { opcion ->
                        DropdownMenuItem(text = { Text(opcion) }, onClick = { estado = opcion; desplegado = false })
                    }
                }
            }
            OutlinedTextField(
                value = observaciones,
                onValueChange = { observaciones = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Observaciones") },
                placeholder = { Text("Describe golpes, faltantes u otros detalles") },
                minLines = 3
            )
            Button(onClick = { confirmado = true }, modifier = Modifier.fillMaxWidth()) {
                Text("Confirmar devolución")
            }
        }
    }

    if (confirmado) {
        AlertDialog(
            onDismissRequest = { confirmado = false },
            title = { Text("Devolución registrada") },
            text = { Text("Notebook #07 · $estado${if (observaciones.isBlank()) "" else "\nObservaciones: $observaciones"}") },
            confirmButton = { TextButton(onClick = { confirmado = false; onBack() }) { Text("Aceptar") } }
        )
    }
}
