package com.example.prestaaula

// Esta es la primera pantalla que Android abre
// Desde aqui se aplica el tema que se eligio y se muestra la navegacion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import com.example.prestaaula.navigation.AppNavigation
import com.example.prestaaula.ui.theme.PrestaAulaTheme
import com.example.prestaaula.ui.viewmodel.SettingsViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Pedimos el ViewModel que guarda la apariencia elegida
            val settingsViewModel: SettingsViewModel = viewModel(factory = SettingsViewModel.Factory)
            // Observamos la preferencia para que Compose actualice los colores
            val themeMode by settingsViewModel.themeMode.collectAsStateWithLifecycle()

            PrestaAulaTheme(themeMode = themeMode) {
                AppNavigation(
                    themeMode = themeMode,
                    onThemeModeChange = settingsViewModel::setThemeMode
                )
            }
        }
    }
}
