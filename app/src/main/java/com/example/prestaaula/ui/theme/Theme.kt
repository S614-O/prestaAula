package com.example.prestaaula.ui.theme

// NOTAS DE AMARO Y SOTO
// Junta la paleta de colores y la tipografía para que Compose use el mismo estilo en toda la app.
// La idea de estos comentarios es explicar el código como apunte de clase.


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Relacionamos los colores con los nombres que entiende Material 3.
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
// Las pantallas envueltas en este tema heredan colores y tipografía.
fun PrestaAulaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = PrestaAulaColorScheme,
        typography = PrestaAulaTypography,
        content = content
    )
}
