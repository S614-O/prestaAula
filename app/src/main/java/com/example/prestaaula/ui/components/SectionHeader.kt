package com.example.prestaaula.ui.components

// NOTAS DE AMARO Y SOTO
// Título reutilizable para separar secciones de una pantalla.
// La idea de estos comentarios es explicar el código como apunte de clase.


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.prestaaula.ui.theme.AzulPetroleo

@Composable
// Este título se usa en varias pantallas para separar listas por tema.
fun SectionHeader(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier.fillMaxWidth().padding(top = 12.dp, bottom = 4.dp),
        color = AzulPetroleo,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.labelLarge
    )
}
