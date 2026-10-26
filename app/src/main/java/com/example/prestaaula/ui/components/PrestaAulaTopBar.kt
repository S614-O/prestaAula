package com.example.prestaaula.ui.components

// NOTAS DE AMARO Y SOTO
// Barra superior reutilizable con título, subtítulo y una flecha opcional para volver.
// La idea de estos comentarios es explicar el código como apunte de clase.


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.prestaaula.ui.theme.AzulPetroleo
import com.example.prestaaula.ui.theme.TextoSecundario

@OptIn(ExperimentalMaterial3Api::class)
@Composable
// onBack es opcional: si no llega una acción, no dibujamos la flecha.
fun PrestaAulaTopBar(
    titulo: String,
    subtitulo: String,
    onBack: (() -> Unit)? = null
) {
    TopAppBar(
        title = {
            Column(modifier = Modifier.padding(vertical = 2.dp)) {
                Text(titulo, fontWeight = FontWeight.Bold)
                Text(subtitulo, color = TextoSecundario, style = MaterialTheme.typography.bodySmall)
            }
        },
        navigationIcon = {
            if (onBack != null) {
                IconButton(onClick = onBack) {
                    Text("←", color = AzulPetroleo, style = MaterialTheme.typography.headlineSmall)
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
}
