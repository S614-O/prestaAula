package com.example.prestaaula

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.prestaaula.navigation.AppNavigation
import com.example.prestaaula.ui.theme.PrestaAulaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PrestaAulaTheme {
                AppNavigation()
            }
        }
    }
}
