package com.example.prestaaula.ui.screens

// NOTAS DE AMARO Y SOTO
// Pantalla inicial. Muestra el nombre del sistema y las tarjetas para entrar como docente, encargado CRA o administración.
// La idea de estos comentarios es explicar el código como apunte de clase.


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.example.prestaaula.model.Rol
import com.example.prestaaula.ui.theme.AzulPetroleo
import com.example.prestaaula.ui.theme.CelesteSuave
import com.example.prestaaula.ui.theme.Fondo

@Composable
// Es el menú principal. Aquí todavía no se consultan datos: solo se elige a qué sección entrar.
fun HomeScreen(
    onDocenteClick: () -> Unit,
    onCraClick: () -> Unit,
    onAdministracionClick: () -> Unit
) {
    Scaffold(containerColor = Fondo) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).verticalScroll(rememberScrollState()).padding(horizontal = 24.dp, vertical = 28.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = AzulPetroleo)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text("PA  ·  CRA", color = CelesteSuave, style = MaterialTheme.typography.labelLarge)
                    Spacer(Modifier.height(10.dp))
                    Text("PrestaAula", color = Color.White, style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(6.dp))
                    Text("Colegio Marcela Paz · CRA", color = Color.White, style = MaterialTheme.typography.bodyLarge)
                    Spacer(Modifier.height(12.dp))
                    Text("Préstamo de recursos tecnológicos", color = CelesteSuave, style = MaterialTheme.typography.bodyMedium)
                }
            }
            Spacer(Modifier.height(28.dp))
            Text("¿Cómo deseas ingresar?", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onBackground)
            Spacer(Modifier.height(14.dp))
            // Cada tarjeta llama al callback que conecta con una ruta en NavGraph.
            RoleCard(Rol.DOCENTE, "▣", onDocenteClick)
            Spacer(Modifier.height(12.dp))
            RoleCard(Rol.ENCARGADO_CRA, "↻", onCraClick)
            Spacer(Modifier.height(12.dp))
            RoleCard(Rol.ADMINISTRACION, "▤", onAdministracionClick)
        }
    }
}

@Composable
// Una sola tarjeta sirve para los tres roles; solo cambia el texto, símbolo y acción.
private fun RoleCard(rol: Rol, simbolo: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(18.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(simbolo, color = AzulPetroleo, style = MaterialTheme.typography.headlineMedium)
            Column(modifier = Modifier.weight(1f)) {
                Text(rol.titulo, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(4.dp))
                Text(rol.descripcion, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Text("›", color = AzulPetroleo, style = MaterialTheme.typography.headlineMedium)
        }
    }
}
