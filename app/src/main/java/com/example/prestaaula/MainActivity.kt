package com.example.prestaaula
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.prestaaula.navigation.AppNavigation
import com.example.prestaaula.ui.theme.PrestaAulaTheme

// esta es la primera Activity que abre Android.
class MainActivity : ComponentActivity() {
    // Aquí armamos la interfaz inicial usando Jetpack Compose.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContent permite dibujar la pantalla con componentes Compose en vez de XML.
        setContent {
            PrestaAulaTheme {
                AppNavigation()
            }
        }
    }
}

//Revisa el libs porque hay una version rara


//ya la revise xd, esta lista

//dale xd
