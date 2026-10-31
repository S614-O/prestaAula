package com.example.prestaaula.ui.theme

// Aqui dejamos los colores que usa toda la app
// Elegimos verdes para relacionar el diseño con el colegio y el CRA
// Los composables usan estos roles desde MaterialTheme y no colores directos

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Modo claro con verdes suaves y fondos claros
val PrestaAulaLightColorScheme = lightColorScheme(
    primary = Color(0xFF246B45),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFD3EBDD),
    onPrimaryContainer = Color(0xFF082F1A),

    secondary = Color(0xFF456F50),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFD7EAD8),
    onSecondaryContainer = Color(0xFF102F19),

    tertiary = Color(0xFF637D36),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFE3EDC5),
    onTertiaryContainer = Color(0xFF202F05),

    error = Color(0xFFBA1A1A),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),

    background = Color(0xFFF4F8F3),
    onBackground = Color(0xFF17251B),
    surface = Color(0xFFFBFDF9),
    onSurface = Color(0xFF17251B),
    surfaceVariant = Color(0xFFE1EAE1),
    onSurfaceVariant = Color(0xFF414B42),

    outline = Color(0xFF718075),
    outlineVariant = Color(0xFFC2CEC2),
    scrim = Color(0xFF000000),
    inverseSurface = Color(0xFF2C332D),
    inverseOnSurface = Color(0xFFEEF2EC),
    inversePrimary = Color(0xFF9BD5AC)
)

// Modo oscuro con verdes mas claros para que se lean sobre fondos profundos
val PrestaAulaDarkColorScheme = darkColorScheme(
    primary = Color(0xFF9BD5AC),
    onPrimary = Color(0xFF07391F),
    primaryContainer = Color(0xFF1C5634),
    onPrimaryContainer = Color(0xFFB9F0C7),

    secondary = Color(0xFFB0D2B4),
    onSecondary = Color(0xFF1C3924),
    secondaryContainer = Color(0xFF344F39),
    onSecondaryContainer = Color(0xFFCDEBD0),

    tertiary = Color(0xFFC4D99A),
    onTertiary = Color(0xFF303F0D),
    tertiaryContainer = Color(0xFF485B20),
    onTertiaryContainer = Color(0xFFE0EFB7),

    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),

    background = Color(0xFF101812),
    onBackground = Color(0xFFE0E8DF),
    surface = Color(0xFF101812),
    onSurface = Color(0xFFE0E8DF),
    surfaceVariant = Color(0xFF3E4A40),
    onSurfaceVariant = Color(0xFFC1CDC1),

    outline = Color(0xFF89968A),
    outlineVariant = Color(0xFF3E4A40),
    scrim = Color(0xFF000000),
    inverseSurface = Color(0xFFE0E8DF),
    inverseOnSurface = Color(0xFF29332B),
    inversePrimary = Color(0xFF246B45)
)
