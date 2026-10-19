package com.example.prestaaula.ui.components

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.prestaaula.navigation.Routes

@Composable
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
