package com.example.prestaaula.ui.components

// NOTAS DE AMARO Y SOTO
// Etiqueta de color para distinguir si el equipo está disponible, prestado o en mantención.
// La idea de estos comentarios es explicar el código como apunte de clase.


import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.prestaaula.model.EstadoEquipo
import com.example.prestaaula.ui.theme.AmarilloMantencion
import com.example.prestaaula.ui.theme.AmarilloTexto
import com.example.prestaaula.ui.theme.RojoPrestado
import com.example.prestaaula.ui.theme.RojoTexto
import com.example.prestaaula.ui.theme.VerdeDisponible
import com.example.prestaaula.ui.theme.VerdeTexto

@Composable
// Elegimos colores según el estado, para entenderlo rápido sin leer todo el detalle.
fun StatusChip(estado: EstadoEquipo) {
    // Cada estado usa un par de colores con buen contraste.
    val (fondo, texto) = when (estado) {
        EstadoEquipo.DISPONIBLE -> VerdeDisponible to VerdeTexto
        EstadoEquipo.PRESTADO -> RojoPrestado to RojoTexto
        EstadoEquipo.MANTENCION -> AmarilloMantencion to AmarilloTexto
    }
    Surface(color = fondo, shape = RoundedCornerShape(50)) {
        Text(
            text = estado.etiqueta,
            color = texto,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
        )
    }
}
