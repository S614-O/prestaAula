package com.example.prestaaula.ui.components

// Esta etiqueta muestra el estado del equipo con colores distintos
// Los tonos salen del tema para que tambien cambien en modo oscuro

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.prestaaula.model.EstadoEquipo
import com.example.prestaaula.ui.theme.PrestaAulaCustomShapes

@Composable
fun StatusChip(estado: EstadoEquipo) {
    // Elegimos el fondo y el texto segun el estado del equipo
    val (fondo, texto) = when (estado) {
        EstadoEquipo.DISPONIBLE ->
            MaterialTheme.colorScheme.tertiaryContainer to MaterialTheme.colorScheme.onTertiaryContainer

        EstadoEquipo.PRESTADO ->
            MaterialTheme.colorScheme.errorContainer to MaterialTheme.colorScheme.onErrorContainer

        EstadoEquipo.MANTENCION ->
            MaterialTheme.colorScheme.secondaryContainer to MaterialTheme.colorScheme.onSecondaryContainer
    }

    Surface(
        color = fondo,
        contentColor = texto,
        shape = PrestaAulaCustomShapes.pill
    ) {
        Text(
            text = estado.etiqueta,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
        )
    }
}
