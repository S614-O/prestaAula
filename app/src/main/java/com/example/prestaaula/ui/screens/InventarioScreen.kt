package com.example.prestaaula.ui.screens

// Lista los equipos guardados en Room separados por categoria Si una categoria esta vacia muestra un mensaje
// La idea de estos comentarios es explicar el codigo como apunte de clase


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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.prestaaula.model.Equipo
import com.example.prestaaula.ui.components.EquipoCard
import com.example.prestaaula.ui.components.OperationFeedback
import com.example.prestaaula.ui.components.PrestaAulaBottomBar
import com.example.prestaaula.ui.components.PrestaAulaTopBar
import com.example.prestaaula.ui.components.SectionHeader

@Composable
// Recibe los equipos de Room y los muestra en categorias Las acciones las define NavGraph
fun InventarioScreen(
    notebooks: List<Equipo>,
    proyectores: List<Equipo>,
    otrosEquipos: List<Equipo>,
    cargando: Boolean,
    mensajeError: String?,
    onDismissMessage: () -> Unit,
    onBack: () -> Unit,
    onNavigate: (String) -> Unit,
    onReservar: (String) -> Unit
) {
    // Scaffold organiza barra superior contenido y barra de pestanas
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { PrestaAulaTopBar("Inventario", "Equipos guardados en este dispositivo", onBack) },
        bottomBar = { PrestaAulaBottomBar(selectedRoute = "inventario", onNavigate = onNavigate) }
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
                Text("Inventario del CRA", style = MaterialTheme.typography.titleLarge)
                Text(
                    "Consulta los equipos registrados por el encargado o encargada del CRA.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(10.dp))
                SectionHeader("NOTEBOOKS")
                if (notebooks.isEmpty()) EmptyInventoryCard("No hay notebooks registrados.")
            }
            // LazyColumn dibuja solo los elementos necesarios cuando se desplaza la lista
            items(notebooks, key = { "notebook-${it.id}" }) { equipo ->
                EquipoCard(equipo = equipo, onReservar = onReservar)
            }
            item { SectionHeader("PROYECTORES") }
            if (proyectores.isEmpty()) item { EmptyInventoryCard("No hay proyectores registrados.") }
            items(proyectores, key = { "proyector-${it.id}" }) { equipo ->
                EquipoCard(equipo = equipo, onReservar = onReservar)
            }
            if (otrosEquipos.isNotEmpty()) item { SectionHeader("OTROS EQUIPOS") }
            items(otrosEquipos, key = { "otro-${it.id}" }) { equipo ->
                EquipoCard(equipo = equipo, onReservar = onReservar)
            }
            item { Spacer(Modifier.height(16.dp)) }
        }
    }
}

@Composable
// Mensaje que se muestra cuando todavia no hay equipos de esa categoria
private fun EmptyInventoryCard(message: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(message, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(3.dp))
            Text(
                "El encargado o encargada CRA puede registrar equipos desde su pantalla.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
