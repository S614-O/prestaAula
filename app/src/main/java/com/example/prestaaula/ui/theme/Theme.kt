package com.example.prestaaula.ui.theme

// Este archivo junta los colores la letra y las formas
// Desde aca se decide si la app se ve clara oscura o sigue al sistema

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun PrestaAulaTheme(
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    dynamicColor: Boolean = false, // Usamos la paleta verde fija para mantener la identidad visual
    content: @Composable () -> Unit
) {
    // Primero revisamos si la persona eligio claro oscuro o sistema
    val modoOscuro = when (themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }

    val context = LocalContext.current
    val view = LocalView.current

    // Android 12 o superior puede tomar colores del fondo de pantalla
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && modoOscuro ->
            dynamicDarkColorScheme(context)

        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S ->
            dynamicLightColorScheme(context)

        modoOscuro -> PrestaAulaDarkColorScheme
        else -> PrestaAulaLightColorScheme
    }

    // Cambiamos tambien la barra superior del telefono para que combine con el tema
    if (!view.isInEditMode) {
        SideEffect {
            val activity = context.findActivity()
            activity?.window?.let { window ->
                window.statusBarColor = colorScheme.primary.toArgb()
                window.navigationBarColor = colorScheme.background.toArgb()

                val barras = WindowCompat.getInsetsController(window, view)
                barras.isAppearanceLightStatusBars = !modoOscuro
                barras.isAppearanceLightNavigationBars = !modoOscuro
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = PrestaAulaTypography,
        shapes = PrestaAulaShapes,
        content = content
    )
}

// Buscamos la Activity aunque el contexto venga dentro de otro contexto
private fun Context.findActivity(): Activity? {
    var actual: Context = this
    while (actual is ContextWrapper) {
        if (actual is Activity) return actual
        actual = actual.baseContext
    }
    return actual as? Activity
}
