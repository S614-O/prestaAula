package com.example.prestaaula.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.prestaaula.model.DummyData
import com.example.prestaaula.ui.components.PrestaAulaTopBar
import com.example.prestaaula.ui.theme.Fondo

private val cursos = listOf("1° Medio A", "2° Medio A", "3° Medio B", "4° Medio A")
private val bloques = listOf(
    "08:00 - 08:45 (disponible)",
    "08:45 - 09:30 (disponible)",
    "09:45 - 10:30 (disponible)",
    "10:45 - 11:30 (disponible)",
    "11:30 - 12:15 (disponible)",
    "12:15 - 13:00 (disponible)"
)

@Composable
fun NuevaReservaScreen(
    equipoId: String,
    onBack: () -> Unit,
    onConfirmar: (equipoNombre: String, docente: String, curso: String, fecha: String, horario: String) -> Unit
) {
    var docente by remember { mutableStateOf("Prof. Adrovez") }
    var curso by remember { mutableStateOf("1° Medio A") }
    var fecha by remember { mutableStateOf("20-10-2026") }
    var bloque by remember { mutableStateOf(bloques.first()) }
    var error by remember { mutableStateOf(false) }
    val equipo = DummyData.equipos.firstOrNull { it.id == equipoId }
    val equipoNombre = equipo?.nombre ?: "Equipo $equipoId"

    Scaffold(
        containerColor = Fondo,
        topBar = { PrestaAulaTopBar("Nueva reserva", "Completa los datos del préstamo", onBack) }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).verticalScroll(rememberScrollState()).padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text("Equipo seleccionado", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(equipoNombre, style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.primary)
            OutlinedTextField(
                value = docente,
                onValueChange = { docente = it; error = false },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Docente") },
                placeholder = { Text("Ej. Prof. Adrovez") },
                singleLine = true
            )
            DropdownSelector(label = "Curso", selected = curso, options = cursos, onSelected = { curso = it })
            OutlinedTextField(
                value = fecha,
                onValueChange = { fecha = it; error = false },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Fecha (DD-MM-AAAA)") },
                placeholder = { Text("20-10-2026") },
                singleLine = true
            )
            DropdownSelector(label = "Bloque horario", selected = bloque, options = bloques, onSelected = { bloque = it })
            if (error) {
                Text("Ingresa el nombre del docente y una fecha para continuar.", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(Modifier.height(4.dp))
            Button(
                onClick = {
                    if (docente.isNotBlank() && fecha.isNotBlank()) onConfirmar(equipoNombre, docente, curso, fecha, bloque)
                    else error = true
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Confirmar reserva") }
            TextButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text("Cancelar") }
        }
    }
}

@Composable
private fun DropdownSelector(
    label: String,
    selected: String,
    options: List<String>,
    onSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Column {
        Text(label, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.height(4.dp))
        OutlinedButton(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(selected)
                Text("⌄")
            }
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { opcion ->
                DropdownMenuItem(
                    text = { Text(opcion) },
                    onClick = { onSelected(opcion); expanded = false }
                )
            }
        }
    }
}
