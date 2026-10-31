package com.example.prestaaula.ui.screens

// Esta es la pantalla del encargado CRA para registrar equipos y recibir devoluciones
// Los textos y valores de los formularios vienen del ViewModel

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
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.prestaaula.model.Reserva
import com.example.prestaaula.ui.components.OperationFeedback
import com.example.prestaaula.ui.components.PrestaAulaTopBar
import com.example.prestaaula.ui.viewmodel.DevolucionFormularioUiState
import com.example.prestaaula.ui.viewmodel.EquipoFormularioUiState

@Composable
fun RegistrarDevolucionScreen(
    reservas: List<Reserva>,
    cargandoDatos: Boolean,
    formularioEquipo: EquipoFormularioUiState,
    formulariosDevolucion: Map<String, DevolucionFormularioUiState>,
    operacionEnCurso: Boolean,
    mensajeError: String?,
    mensajeExito: String?,
    onDismissMessage: () -> Unit,
    onIniciarFormularioEquipo: () -> Unit,
    onNombreEquipoChange: (String) -> Unit,
    onDescripcionEquipoChange: (String) -> Unit,
    onCategoriaEquipoChange: (String) -> Unit,
    onEstadoDevolucionChange: (String, String) -> Unit,
    onObservacionesChange: (String, String) -> Unit,
    onBack: () -> Unit,
    onAgregarEquipo: ((Boolean) -> Unit) -> Unit,
    onConfirmarDevolucion: (String) -> Unit
) {
    // Este valor solamente controla si el dialogo esta abierto
    var mostrarDialogoEquipo by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { PrestaAulaTopBar("Gestión CRA", "Registrar equipos y devoluciones", onBack) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            if (mensajeError != null || mensajeExito != null) {
                item { OperationFeedback(mensajeError, mensajeExito, onDismissMessage) }
            }
            if (cargandoDatos || operacionEnCurso) {
                item { LinearProgressIndicator(modifier = Modifier.fillMaxWidth()) }
            }
            item {
                Text("Inventario del CRA", style = MaterialTheme.typography.titleLarge)
                Text(
                    "Registra nuevos recursos tecnológicos para que estén disponibles en el inventario.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Button(
                    onClick = {
                        onIniciarFormularioEquipo()
                        mostrarDialogoEquipo = true
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !operacionEnCurso
                ) {
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
                                "Las reservas aparecerán aquí después de pulsar Confirmar retiro en Mis reservas.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
            items(reservas, key = { it.id }) { reserva ->
                DevolucionCard(
                    reserva = reserva,
                    formulario = formulariosDevolucion[reserva.id] ?: DevolucionFormularioUiState(),
                    operacionEnCurso = operacionEnCurso,
                    onEstadoChange = { estado -> onEstadoDevolucionChange(reserva.id, estado) },
                    onObservacionesChange = { texto -> onObservacionesChange(reserva.id, texto) },
                    onConfirmar = { onConfirmarDevolucion(reserva.id) }
                )
            }
        }
    }

    if (mostrarDialogoEquipo) {
        RegistrarEquipoDialog(
            formulario = formularioEquipo,
            guardando = operacionEnCurso,
            mensajeError = mensajeError,
            onDismiss = { mostrarDialogoEquipo = false },
            onNombreChange = onNombreEquipoChange,
            onDescripcionChange = onDescripcionEquipoChange,
            onCategoriaChange = onCategoriaEquipoChange,
            onGuardar = onAgregarEquipo,
            alGuardar = { guardado -> if (guardado) mostrarDialogoEquipo = false },
            onDismissMessage = onDismissMessage
        )
    }
}

@Composable
// Tarjeta que permite registrar que el equipo ya fue devuelto
private fun DevolucionCard(
    reserva: Reserva,
    formulario: DevolucionFormularioUiState,
    operacionEnCurso: Boolean,
    onEstadoChange: (String) -> Unit,
    onObservacionesChange: (String) -> Unit,
    onConfirmar: () -> Unit
) {
    val estados = listOf("Buen estado", "En mantenimiento", "Daño / Incidencia")
    var opcionesAbiertas by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(9.dp)) {
            Text(reserva.equipoNombre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("${reserva.docente} · ${reserva.curso}", style = MaterialTheme.typography.bodyMedium)
            Text(
                "${reserva.fecha} · ${reserva.horario}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text("Estado al devolver", style = MaterialTheme.typography.labelLarge)
            OutlinedButton(
                onClick = { opcionesAbiertas = true },
                modifier = Modifier.fillMaxWidth(),
                enabled = !operacionEnCurso
            ) {
                Text(formulario.estado)
                Spacer(Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = "Estado seleccionado",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            DropdownMenu(expanded = opcionesAbiertas, onDismissRequest = { opcionesAbiertas = false }) {
                estados.forEach { opcion ->
                    DropdownMenuItem(text = { Text(opcion) }, onClick = {
                        onEstadoChange(opcion)
                        opcionesAbiertas = false
                    })
                }
            }
            OutlinedTextField(
                value = formulario.observaciones,
                onValueChange = onObservacionesChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Observaciones") },
                placeholder = { Text("Observaciones (opcional)") },
                enabled = !operacionEnCurso,
                supportingText = {
                    Text(
                        if (formulario.estado == "Buen estado") {
                            "Campo opcional"
                        } else {
                            "Puedes dejar una observacion sobre el problema"
                        }
                    )
                }
            )
            Button(
                onClick = onConfirmar,
                modifier = Modifier.fillMaxWidth(),
                // Dejamos que el ViewModel revise si faltan observaciones
                enabled = !operacionEnCurso
            ) { Text("Confirmar devolución") }
        }
    }
}

@Composable
// Dialogo para escribir los datos del equipo que se va a agregar
private fun RegistrarEquipoDialog(
    formulario: EquipoFormularioUiState,
    guardando: Boolean,
    mensajeError: String?,
    onDismiss: () -> Unit,
    onNombreChange: (String) -> Unit,
    onDescripcionChange: (String) -> Unit,
    onCategoriaChange: (String) -> Unit,
    onGuardar: ((Boolean) -> Unit) -> Unit,
    alGuardar: (Boolean) -> Unit,
    onDismissMessage: () -> Unit
) {
    // Solo queda local el estado de abrir el menu de categorias
    var categoriaAbierta by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Registrar equipo") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = formulario.nombre,
                    onValueChange = onNombreChange,
                    label = { Text("Nombre del equipo") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    enabled = !guardando,
                    isError = formulario.errorNombre != null,
                    supportingText = {
                        formulario.errorNombre?.let {
                            Text(it, color = MaterialTheme.colorScheme.error)
                        }
                    },
                    trailingIcon = {
                        if (formulario.errorNombre != null) {
                            Icon(
                                imageVector = Icons.Filled.ErrorOutline,
                                contentDescription = "Revisar nombre",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                )
                OutlinedTextField(
                    value = formulario.descripcion,
                    onValueChange = onDescripcionChange,
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !guardando,
                    isError = formulario.errorDescripcion != null,
                    supportingText = {
                        formulario.errorDescripcion?.let {
                            Text(it, color = MaterialTheme.colorScheme.error)
                        }
                    },
                    trailingIcon = {
                        if (formulario.errorDescripcion != null) {
                            Icon(
                                imageVector = Icons.Filled.ErrorOutline,
                                contentDescription = "Revisar descripcion",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                )
                Text("Categoría", style = MaterialTheme.typography.labelLarge)
                OutlinedButton(
                    onClick = { categoriaAbierta = true },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !guardando
                ) { Text(formulario.categoria) }
                DropdownMenu(expanded = categoriaAbierta, onDismissRequest = { categoriaAbierta = false }) {
                    listOf("NOTEBOOKS", "PROYECTORES").forEach { opcion ->
                        DropdownMenuItem(text = { Text(opcion) }, onClick = {
                            onCategoriaChange(opcion)
                            categoriaAbierta = false
                        })
                    }
                }
                if (mensajeError != null) {
                    Text(mensajeError, color = MaterialTheme.colorScheme.error)
                    TextButton(onClick = onDismissMessage) { Text("Cerrar mensaje") }
                }
            }
        },
        confirmButton = {
            TextButton(
                // El ViewModel marca los campos que falten despues de intentar guardar
                enabled = !guardando,
                onClick = { onGuardar(alGuardar) }
            ) { Text(if (guardando) "Guardando..." else "Guardar") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !guardando) { Text("Cancelar") }
        }
    )
}
