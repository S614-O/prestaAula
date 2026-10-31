package com.example.prestaaula.ui.screens

// Esta es la pantalla principal donde se elige el tipo de usuario
// Dejamos el acceso de docente mas grande porque es la opcion principal
// Las otras dos opciones quedan abajo para que se vea mas ordenado

import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.prestaaula.model.Rol
import com.example.prestaaula.ui.components.ThemeSwitcher
import com.example.prestaaula.ui.theme.PrestaAulaElevation
import com.example.prestaaula.ui.theme.ThemeMode

@Composable
fun HomeScreen(
    themeMode: ThemeMode,
    onThemeModeChange: (ThemeMode) -> Unit,
    onDocenteClick: () -> Unit,
    onCraClick: () -> Unit,
    onAdministracionClick: () -> Unit
) {
    var mostrarTema by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // Arriba dejamos el nombre del colegio y el acceso para cambiar el tema
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        "COLEGIO MARCELA PAZ",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        "PrestaAula",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                TextButton(onClick = { mostrarTema = true }) {
                    Text("Apariencia")
                }
            }

            // Esta tarjeta es la mas grande y sirve para entrar como docente
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.extraLarge,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = PrestaAulaElevation.Level3
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Usamos un icono de Material en vez de escribir un simbolo como texto
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Acceso para docentes",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.height(38.dp)
                    )
                    Text(
                        "Acceso principal",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
                    )
                    Text(
                        Rol.DOCENTE.titulo,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Revisa el inventario y solicita equipos para tus clases",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(Modifier.height(2.dp))
                    Button(
                        onClick = onDocenteClick,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onPrimary,
                            contentColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Ingresar como docente")
                    }
                }
            }

            Text(
                "Otros accesos",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            // El encargado CRA y UTP quedan como tarjetas mas compactas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CompactRoleCard(
                    modifier = Modifier.weight(1f),
                    simbolo = "↻",
                    titulo = "Encargado CRA",
                    descripcion = "Equipos y devoluciones",
                    onClick = onCraClick
                )
                CompactRoleCard(
                    modifier = Modifier.weight(1f),
                    simbolo = "▤",
                    titulo = "UTP / Administración",
                    descripcion = "Reportes y actividad",
                    onClick = onAdministracionClick
                )
            }

            Text(
                "Sistema de préstamo de recursos tecnológicos",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }

    // Este dialogo deja cambiar entre tema claro oscuro o sistema
    if (mostrarTema) {
        AlertDialog(
            onDismissRequest = { mostrarTema = false },
            title = {
                Text(
                    "Apariencia de PrestaAula",
                    style = MaterialTheme.typography.titleLarge
                )
            },
            text = {
                ThemeSwitcher(
                    currentMode = themeMode,
                    onModeChange = onThemeModeChange
                )
            },
            confirmButton = {
                TextButton(onClick = { mostrarTema = false }) {
                    Text("Listo")
                }
            }
        )
    }
}

@Composable
// Esta tarjeta se reutiliza para los accesos secundarios
private fun CompactRoleCard(
    modifier: Modifier = Modifier,
    simbolo: String,
    titulo: String,
    descripcion: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = PrestaAulaElevation.Level1
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                simbolo,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                titulo,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                descripcion,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                "Ingresar  ›",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
