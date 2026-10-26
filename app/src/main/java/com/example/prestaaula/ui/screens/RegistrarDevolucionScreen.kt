package com.example.prestaaula.ui.screens

// NOTAS DE AMARO Y SOTO
// Pantalla del encargado CRA para registrar nuevos equipos y confirmar las devoluciones pendientes.
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.prestaaula.model.Reserva
import com.example.prestaaula.ui.components.PrestaAulaTopBar
import com.example.prestaaula.ui.theme.Fondo

@Composable
// Sección del CRA: registra equipos y muestra los préstamos que deben volver.
fun RegistrarDevolucionScreen(
    reservas: List<Reserva>,
    onBack: () -> Unit,
    onAgregarEquipo: (String, String, String) -> Unit,
    onConfirmarDevolucion: (String, String, String) -> Unit
) {
    // Este estado abre o cierra la ventana para registrar un equipo nuevo.
    var mostrarDialogoEquipo by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Fondo,
        topBar = { PrestaAulaTopBar("Gestión CRA", "Registrar equipos y devoluciones", onBack) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Text("Inventario del CRA", style = MaterialTheme.typography.titleLarge)
                Text(
                    "Registra nuevos recursos tecnológicos para que estén disponibles en el inventario.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Button(onClick = { mostrarDialogoEquipo = true }, modifier = Modifier.fillMaxWidth()) {
                    Text("+ Registrar equipo")
                }
                Spacer(Modifier.height(10.dp))
                Text("Devoluciones pendientes", style = MaterialTheme.typography.titleLarge)
                if (reservas.isEmpty()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(Modifier.padding(18.dp)) {
                            Text("No hay devoluciones pendientes.", style = MaterialTheme.typography.titleMedium)
                            Spacer(Modifier.height(6.dp))
                            Text(
                                "Las reservas aparecerán aquí después de pulsar «Confirmar retiro» en Mis reservas.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
            // Cada reserva retirada se muestra con su formulario de devolución.
            items(reservas, key = { it.id }) { reserva ->
                DevolucionCard(reserva = reserva, onConfirmar = onConfirmarDevolucion)
            }
        }
    }

    if (mostrarDialogoEquipo) {
        RegistrarEquipoDialog(
            onDismiss = { mostrarDialogoEquipo = false },
            onGuardar = { nombre, descripcion, categoria ->
                onAgregarEquipo(nombre, descripcion, categoria)
                mostrarDialogoEquipo = false
            }
        )
    }
}

@Composable
// Formulario de una devolución: estado al recibir el equipo y observaciones.
private fun DevolucionCard(
    reserva: Reserva,
    onConfirmar: (String, String, String) -> Unit
) {
    val estados = listOf("Buen estado", "En mantenimiento", "Daño / Incidencia")
    var estado by remember(reserva.id) { mutableStateOf(estados.first()) }
    var observaciones by remember(reserva.id) { mutableStateOf("") }
    var opcionesAbiertas by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(9.dp)) {
            Text(reserva.equipoNombre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("${reserva.docente} · ${reserva.curso}", style = MaterialTheme.typography.bodyMedium)
            Text("${reserva.fecha} · ${reserva.horario}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("Estado al devolver", style = MaterialTheme.typography.labelLarge)
            OutlinedButton(onClick = { opcionesAbiertas = true }, modifier = Modifier.fillMaxWidth()) { Text(estado) }
            DropdownMenu(expanded = opcionesAbiertas, onDismissRequest = { opcionesAbiertas = false }) {
                estados.forEach { opcion ->
                    DropdownMenuItem(text = { Text(opcion) }, onClick = {
                        estado = opcion
                        opcionesAbiertas = false
                    })
                }
            }
            OutlinedTextField(
                value = observaciones,
                onValueChange = { observaciones = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Observaciones") },
                placeholder = { Text("Observaciones (opcional)") }
            )
            Button(
                onClick = { onConfirmar(reserva.id, estado, observaciones) },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Confirmar devolución") }
        }
    }
}

@Composable
// Ventana donde el encargado escribe los datos del equipo que agrega al inventario.
private fun RegistrarEquipoDialog(
    onDismiss: () -> Unit,
    onGuardar: (String, String, String) -> Unit
) {
    // Los datos del nuevo equipo se guardan primero en el estado del formulario.
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("NOTEBOOKS") }
    var categoriaAbierta by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Registrar equipo") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it; error = false },
                    label = { Text("Nombre del equipo") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it; error = false },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth()
                )
                Text("Categoría", style = MaterialTheme.typography.labelLarge)
                OutlinedButton(onClick = { categoriaAbierta = true }, modifier = Modifier.fillMaxWidth()) {
                    Text(categoria)
                }
                DropdownMenu(expanded = categoriaAbierta, onDismissRequest = { categoriaAbierta = false }) {
                    listOf("NOTEBOOKS", "PROYECTORES").forEach { opcion ->
                        DropdownMenuItem(text = { Text(opcion) }, onClick = {
                            categoria = opcion
                            categoriaAbierta = false
                        })
                    }
                }
                if (error) Text("Ingresa el nombre y la descripción del equipo.", color = MaterialTheme.colorScheme.error)
            }
        },
        confirmButton = {
            TextButton(onClick = {
                // Evitamos guardar un equipo si faltan nombre o descripción.
                if (nombre.isBlank() || descripcion.isBlank()) error = true
                else onGuardar(nombre.trim(), descripcion.trim(), categoria)
            }) { Text("Guardar") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } }
    )
}
