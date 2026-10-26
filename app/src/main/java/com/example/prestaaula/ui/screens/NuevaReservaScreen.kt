package com.example.prestaaula.ui.screens

// NOTAS DE AMARO Y SOTO
// Formulario para reservar un equipo. Aquí se manejan los campos, el calendario, los cursos y los bloques horarios.
// La idea de estos comentarios es explicar el código como apunte de clase.


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.prestaaula.model.Equipo
import com.example.prestaaula.model.EstadoEquipo
import com.example.prestaaula.ui.components.PrestaAulaTopBar
import com.example.prestaaula.ui.theme.Fondo
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

// Amaro: generamos los cursos desde 1° básico hasta 4° medio, cada uno con A y B.
private val cursosDisponibles: List<String> = buildList {
    for (grado in 1..8) {
        add("$grado° Básico A")
        add("$grado° Básico B")
    }
    for (grado in 1..4) {
        add("$grado° Medio A")
        add("$grado° Medio B")
    }
}

// Horario de referencia de jornada escolar: bloques pedagógicos de 45 minutos,
// recreos y almuerzo excluidos. Los viernes se usa una jornada más corta.
// Horarios de referencia de lunes a jueves, sin incluir recreos ni almuerzo.
private val bloquesLunesAJueves = listOf(
    "08:00 - 08:45",
    "08:45 - 09:30",
    "09:50 - 10:35",
    "10:35 - 11:20",
    "11:40 - 12:25",
    "12:25 - 13:10",
    "13:55 - 14:40",
    "14:40 - 15:25"
)

// El viernes tiene menos bloques porque se considera una jornada más corta.
private val bloquesViernes = listOf(
    "08:00 - 08:45",
    "08:45 - 09:30",
    "09:45 - 10:30",
    "10:30 - 11:15",
    "11:30 - 12:15",
    "12:15 - 13:00"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
// Controla el formulario de reserva y valida que los datos estén completos antes de guardar.
fun NuevaReservaScreen(
    equipo: Equipo?,
    onBack: () -> Unit,
    onReservaCreada: () -> Unit,
    onConfirmar: (docente: String, curso: String, fecha: String, horario: String, resultado: (Boolean) -> Unit) -> Unit
) {
    // remember mantiene los valores mientras esta pantalla sigue activa.
    var docente by remember { mutableStateOf("") }
    var curso by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var horario by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf("") }
    var mostrarCalendario by remember { mutableStateOf(false) }

    // El calendario usa una regla para desactivar fechas pasadas y fines de semana.
    val fechasEscolares = remember { FechasEscolares(todayAtUtcMidnight()) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = null,
        selectableDates = fechasEscolares
    )
    // Cuando cambia la fecha, recalculamos qué horarios se pueden seleccionar.
    val bloquesDisponibles = remember(fecha) { obtenerBloquesParaFecha(fecha) }
    val equipoDisponible = equipo != null && equipo.estado == EstadoEquipo.DISPONIBLE
    // El botón solo se habilita cuando hay equipo disponible y los campos son válidos.
    val formularioValido = equipoDisponible && docente.isNotBlank() && curso.isNotBlank() &&
        fechaValida(fecha) && horario in bloquesDisponibles

    Scaffold(
        containerColor = Fondo,
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
                text = equipo?.nombre ?: "Equipo no disponible",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )

            OutlinedTextField(
                value = docente,
                onValueChange = { docente = it; mensajeError = "" },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Docente") },
                placeholder = { Text("Nombre y apellido") },
                singleLine = true
            )

            SelectorDropdown(
                label = "Curso",
                placeholder = "Selecciona el curso",
                value = curso,
                opciones = cursosDisponibles,
                onSeleccionar = { curso = it; mensajeError = "" }
            )

            OutlinedTextField(
                value = fecha,
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { mostrarCalendario = true },
                label = { Text("Fecha de reserva") },
                placeholder = { Text("Selecciona una fecha") },
                trailingIcon = {
                    TextButton(onClick = { mostrarCalendario = true }) {
                        Text("Calendario")
                    }
                },
                readOnly = true,
                singleLine = true
            )
            Text(
                "Solo se pueden reservar días hábiles (lunes a viernes), desde hoy.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            SelectorDropdown(
                label = "Bloque horario",
                placeholder = if (fecha.isBlank()) "Primero selecciona una fecha" else "Selecciona un bloque",
                value = horario,
                opciones = bloquesDisponibles,
                enabled = bloquesDisponibles.isNotEmpty(),
                onSeleccionar = { horario = it; mensajeError = "" }
            )
            if (fecha.isNotBlank()) {
                Text(
                    if (esViernes(fecha)) "Horario de viernes: jornada hasta las 13:00." 
                    else "Horario de lunes a jueves: considera recreos y almuerzo.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (!equipoDisponible) {
                Text(
                    "El equipo ya no está disponible. Vuelve al inventario y selecciona uno disponible.",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            if (mensajeError.isNotBlank()) {
                Text(mensajeError, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(Modifier.height(4.dp))
            Button(
                onClick = {
                    onConfirmar(docente.trim(), curso, fecha, horario) { creada ->
                        if (creada) onReservaCreada()
                        else mensajeError = "No se pudo guardar la reserva. Comprueba que el equipo siga disponible y que no exista otra reserva para ese horario."
                    }
                },
                enabled = formularioValido,
                modifier = Modifier.fillMaxWidth()
            ) { Text("Confirmar reserva") }
            TextButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text("Cancelar") }
        }
    }

    // El diálogo solo aparece cuando el usuario pulsa el campo de fecha.
    if (mostrarCalendario) {
        DatePickerDialog(
            onDismissRequest = { mostrarCalendario = false },
            confirmButton = {
                TextButton(
                    enabled = datePickerState.selectedDateMillis != null,
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            fecha = formatoFecha(millis)
                            horario = ""
                            mensajeError = ""
                        }
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
// Menú desplegable reutilizable para curso y bloque horario.
private fun SelectorDropdown(
    label: String,
    placeholder: String,
    value: String,
    opciones: List<String>,
    enabled: Boolean = true,
    onSeleccionar: (String) -> Unit
) {
    // Controla si las opciones del menú están visibles o escondidas.
    var expandido by remember { mutableStateOf(false) }

    androidx.compose.foundation.layout.Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = enabled && opciones.isNotEmpty()) { expandido = true },
            label = { Text(label) },
            placeholder = { Text(placeholder) },
            trailingIcon = {
                TextButton(
                    enabled = enabled && opciones.isNotEmpty(),
                    onClick = { expandido = true }
                ) { Text("Elegir") }
            },
            readOnly = true,
            enabled = enabled,
            singleLine = true
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
// Esta regla del calendario bloquea días anteriores a hoy y sábado/domingo.
private class FechasEscolares(private val primeraFechaUtcMillis: Long) : SelectableDates {
    // DatePicker llama a esta función para saber si se puede pulsar un día.
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

// Convierte la fecha de hoy a medianoche UTC para que el calendario no se corra por la zona horaria.
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

// Convierte la fecha elegida al formato que guardamos: día-mes-año.
private fun formatoFecha(utcMillis: Long): String = SimpleDateFormat(
    "dd-MM-yyyy",
    Locale("es", "CL")
).apply {
    timeZone = TimeZone.getTimeZone("UTC")
}.format(Date(utcMillis))

// Comprueba que la fecha escrita tenga formato correcto y exista realmente.
private fun fechaValida(valor: String): Boolean {
    return try {
        val formato = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).apply { isLenient = false }
        val fechaParseada = formato.parse(valor) ?: return false
        formato.format(fechaParseada) == valor
    } catch (_: Exception) {
        false
    }
}

// Devuelve la lista de horarios según el día escogido.
private fun obtenerBloquesParaFecha(fecha: String): List<String> {
    if (!fechaValida(fecha)) return emptyList()
    return if (esViernes(fecha)) bloquesViernes else bloquesLunesAJueves
}

// Averigua si la fecha elegida es viernes para aplicar la jornada corta.
private fun esViernes(fecha: String): Boolean {
    return try {
        val formato = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).apply {
            isLenient = false
            timeZone = TimeZone.getTimeZone("UTC")
        }
        val fechaParseada = formato.parse(fecha) ?: return false
        val calendario = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
            time = fechaParseada
        }
        calendario.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY
    } catch (_: Exception) {
        false
    }
}
