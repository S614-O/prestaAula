package com.example.prestaaula.ui.screens

// NOTAS DE AMARO Y SOTO
// Lista los equipos guardados en Room, separados por categoría. Si una categoría está vacía, muestra un mensaje.
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.prestaaula.model.Equipo
import com.example.prestaaula.ui.components.EquipoCard
import com.example.prestaaula.ui.components.PrestaAulaBottomBar
import com.example.prestaaula.ui.components.PrestaAulaTopBar
import com.example.prestaaula.ui.components.SectionHeader
import com.example.prestaaula.ui.theme.Fondo

@Composable
// Recibe los equipos de Room y los muestra en categorías. Las acciones las define NavGraph.
fun InventarioScreen(
    equipos: List<Equipo>,
    onBack: () -> Unit,
    onNavigate: (String) -> Unit,
    onReservar: (String) -> Unit
) {
    // Separamos por categoría para que la lista sea más fácil de revisar.
    val notebooks = equipos.filter { it.categoria.equals("NOTEBOOKS", true) || it.categoria.equals("NOTEBOOK", true) }
    val proyectores = equipos.filter { it.categoria.equals("PROYECTORES", true) || it.categoria.equals("PROYECTOR", true) }
    val otros = equipos.filterNot { it in notebooks || it in proyectores }

    // Scaffold organiza barra superior, contenido y barra de pestañas.
    Scaffold(
        containerColor = Fondo,
        topBar = { PrestaAulaTopBar("Inventario", "Equipos guardados en este dispositivo", onBack) },
        bottomBar = { PrestaAulaBottomBar(selectedRoute = "inventario", onNavigate = onNavigate) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
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
            // LazyColumn dibuja solo los elementos necesarios cuando se desplaza la lista.
            items(notebooks, key = { "notebook-${it.id}" }) { equipo ->
                EquipoCard(equipo = equipo, onReservar = onReservar)
            }
            item { SectionHeader("PROYECTORES") }
            if (proyectores.isEmpty()) item { EmptyInventoryCard("No hay proyectores registrados.") }
            items(proyectores, key = { "proyector-${it.id}" }) { equipo ->
                EquipoCard(equipo = equipo, onReservar = onReservar)
            }
            if (otros.isNotEmpty()) item { SectionHeader("OTROS EQUIPOS") }
            items(otros, key = { "otro-${it.id}" }) { equipo ->
                EquipoCard(equipo = equipo, onReservar = onReservar)
            }
            item { Spacer(Modifier.height(16.dp)) }
        }
    }
}

@Composable
// Mensaje que se muestra cuando todavía no hay equipos de esa categoría.
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
