package com.example.prestaaula.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val PrestaAulaColorScheme = lightColorScheme(
    primary = AzulPetroleo,
    onPrimary = Color.White,
    secondary = AzulMedio,
    background = Fondo,
    onBackground = TextoPrincipal,
    surface = Color.White,
    onSurface = TextoPrincipal
)

@Composable
fun PrestaAulaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = PrestaAulaColorScheme,
        typography = PrestaAulaTypography,
        content = content
    )
}
