package com.example.prestaaula.ui.screens

// Esta pantalla muestra el formulario y manda los cambios al ViewModel
// Solo dejamos aqui cosas visuales como abrir el calendario o los menus

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.prestaaula.model.Equipo
import com.example.prestaaula.ui.components.PrestaAulaTopBar
import com.example.prestaaula.ui.viewmodel.ReservaFormularioUiState
import java.util.Calendar
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevaReservaScreen(
    equipo: Equipo?,
    formulario: ReservaFormularioUiState,
    cargandoDatos: Boolean,
    operacionEnCurso: Boolean,
    mensajeError: String?,
    onDismissMessage: () -> Unit,
    onDocenteChange: (String) -> Unit,
    onCursoChange: (String) -> Unit,
    onFechaSeleccionada: (Long) -> Unit,
    onHorarioChange: (String) -> Unit,
    onBack: () -> Unit,
    onReservaCreada: () -> Unit,
    onConfirmar: ((Boolean) -> Unit) -> Unit
) {
    // Abrir o cerrar el calendario es un estado visual y por eso se queda en la pantalla
    var mostrarCalendario by remember { mutableStateOf(false) }

    // El calendario no deja marcar fechas anteriores ni sabados ni domingos
    val fechasEscolares = remember { FechasEscolares(todayAtUtcMidnight()) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = null,
        selectableDates = fechasEscolares
    )

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { PrestaAulaTopBar("Nueva reserva", "Completa los datos del préstamo", onBack) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                "Equipo seleccionado",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = equipo?.nombre ?: if (cargandoDatos) "Cargando equipo..." else "Equipo no disponible",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )

            OutlinedTextField(
                value = formulario.docente,
                onValueChange = onDocenteChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Docente") },
                placeholder = { Text("Nombre y apellido") },
                singleLine = true,
                isError = formulario.errorDocente != null,
                supportingText = {
                    formulario.errorDocente?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                trailingIcon = {
                    if (formulario.errorDocente != null) {
                        Icon(
                            Icons.Filled.ErrorOutline,
                            contentDescription = "Revisar docente",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )

            SelectorDropdown(
                label = "Curso",
                placeholder = "Selecciona el curso",
                value = formulario.curso,
                opciones = formulario.cursosDisponibles,
                error = formulario.errorCurso,
                onSeleccionar = onCursoChange
            )

            OutlinedTextField(
                value = formulario.fecha,
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { mostrarCalendario = true },
                label = { Text("Fecha de reserva") },
                placeholder = { Text("Selecciona una fecha") },
                trailingIcon = {
                    if (formulario.errorFecha != null) {
                        Icon(
                            Icons.Filled.ErrorOutline,
                            contentDescription = "Revisar fecha",
                            tint = MaterialTheme.colorScheme.error
                        )
                    } else {
                        TextButton(onClick = { mostrarCalendario = true }) {
                            Text("Calendario")
                        }
                    }
                },
                readOnly = true,
                singleLine = true,
                isError = formulario.errorFecha != null,
                supportingText = {
                    formulario.errorFecha?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                }
            )
            Text(
                "Solo se pueden reservar días hábiles desde hoy",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            SelectorDropdown(
                label = "Bloque horario",
                placeholder = if (formulario.fecha.isBlank()) "Primero selecciona una fecha" else "Selecciona un bloque",
                value = formulario.horario,
                opciones = formulario.bloquesDisponibles,
                error = formulario.errorHorario,
                onSeleccionar = onHorarioChange
            )
            if (formulario.fecha.isNotBlank()) {
                Text(
                    if (formulario.esViernes) "Horario de viernes: jornada hasta las 13:00." else "Horario de lunes a jueves con recreos y almuerzo.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (!formulario.equipoDisponible) {
                Text(
                    formulario.errorEquipo ?: "El equipo no está disponible para reservar. Vuelve al inventario y selecciona otro.",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            if (mensajeError != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text(mensajeError, color = MaterialTheme.colorScheme.onErrorContainer)
                        TextButton(onClick = onDismissMessage) { Text("Cerrar") }
                    }
                }
            }

            Spacer(Modifier.height(4.dp))
            Button(
                onClick = {
                    onConfirmar { creada ->
                        if (creada) onReservaCreada()
                    }
                },
                // El ViewModel revisa los campos al pulsar y despues muestra los indicadores
                enabled = !operacionEnCurso,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (operacionEnCurso) "Guardando..." else "Confirmar reserva")
            }
            TextButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
                Text("Cancelar")
            }
        }
    }

    // El dialogo aparece solo cuando el usuario necesita elegir la fecha
    if (mostrarCalendario) {
        DatePickerDialog(
            onDismissRequest = { mostrarCalendario = false },
            confirmButton = {
                TextButton(
                    enabled = datePickerState.selectedDateMillis != null,
                    onClick = {
                        datePickerState.selectedDateMillis?.let(onFechaSeleccionada)
                        mostrarCalendario = false
                    }
                ) { Text("Aceptar") }
            },
            dismissButton = {
                TextButton(onClick = { mostrarCalendario = false }) { Text("Cancelar") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Composable
// Este menu se usa para escoger curso y bloque horario
private fun SelectorDropdown(
    label: String,
    placeholder: String,
    value: String,
    opciones: List<String>,
    enabled: Boolean = true,
    error: String? = null,
    onSeleccionar: (String) -> Unit
) {
    // Solo guardamos si el menu esta abierto porque es parte de la interfaz
    var expandido by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = enabled && opciones.isNotEmpty()) { expandido = true },
            label = { Text(label) },
            placeholder = { Text(placeholder) },
            trailingIcon = {
                if (error != null) {
                    Icon(
                        Icons.Filled.ErrorOutline,
                        contentDescription = "Revisar $label",
                        tint = MaterialTheme.colorScheme.error
                    )
                } else {
                    TextButton(
                        enabled = enabled && opciones.isNotEmpty(),
                        onClick = { expandido = true }
                    ) { Text("Elegir") }
                }
            },
            readOnly = true,
            enabled = enabled,
            singleLine = true,
            isError = error != null,
            supportingText = {
                error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            }
        )
        DropdownMenu(
            expanded = expandido,
            onDismissRequest = { expandido = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            opciones.forEach { opcion ->
                DropdownMenuItem(
                    text = { Text(opcion) },
                    onClick = {
                        onSeleccionar(opcion)
                        expandido = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
// Reglas visuales del calendario para desactivar dias que no son de clases
private class FechasEscolares(private val primeraFechaUtcMillis: Long) : SelectableDates {
    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        if (utcTimeMillis < primeraFechaUtcMillis) return false
        val calendario = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
            timeInMillis = utcTimeMillis
        }
        val dia = calendario.get(Calendar.DAY_OF_WEEK)
        return dia != Calendar.SATURDAY && dia != Calendar.SUNDAY
    }

    override fun isSelectableYear(year: Int): Boolean {
        val calendario = Calendar.getInstance()
        return year >= calendario.get(Calendar.YEAR)
    }
}

// El calendario trabaja con UTC para que la fecha no cambie por la zona horaria
private fun todayAtUtcMidnight(): Long {
    val hoyLocal = Calendar.getInstance()
    return Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
        clear()
        set(
            hoyLocal.get(Calendar.YEAR),
            hoyLocal.get(Calendar.MONTH),
            hoyLocal.get(Calendar.DAY_OF_MONTH),
            0,
            0,
            0
        )
    }.timeInMillis
}
