package com.example.prestaaula.ui.components

// NOTAS DE AMARO Y SOTO
// Barra inferior compartida entre Inventario y Mis reservas.
// La idea de estos comentarios es explicar el código como apunte de clase.


import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.prestaaula.navigation.Routes

@Composable
// La pestaña activa se marca comparando la ruta actual con cada opción.
fun PrestaAulaBottomBar(
    selectedRoute: String,
    onNavigate: (String) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = selectedRoute == Routes.INVENTARIO,
            onClick = { onNavigate(Routes.INVENTARIO) },
            icon = { Text("▦") },
            label = { Text("Inventario") },
            alwaysShowLabel = true
        )
        NavigationBarItem(
            selected = selectedRoute == Routes.MIS_RESERVAS,
            onClick = { onNavigate(Routes.MIS_RESERVAS) },
            icon = { Text("▤") },
            label = { Text("Mis reservas") },
            alwaysShowLabel = true
        )
    }
}
