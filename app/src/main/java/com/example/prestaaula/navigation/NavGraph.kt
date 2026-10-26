package com.example.prestaaula.navigation
// Aquí se declaran las pantallas y cómo pasar de una a otra
// También se cuida que Atrás no deje la app en blanco, porque antes habia un error

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.prestaaula.model.Equipo
import com.example.prestaaula.model.Reserva
import com.example.prestaaula.ui.screens.HomeScreen
import com.example.prestaaula.ui.screens.InventarioScreen
import com.example.prestaaula.ui.screens.MisReservasScreen
import com.example.prestaaula.ui.screens.NuevaReservaScreen
import com.example.prestaaula.ui.screens.RegistrarDevolucionScreen
import com.example.prestaaula.ui.screens.ReportesScreen

@Composable
//aqui conectamos cada ruta con la pantalla que debe aparecer
fun NavGraph(
    equipos: List<Equipo>,
    reservas: List<Reserva>,
    devolucionesPendientes: List<Reserva>,
    onAgregarEquipo: (String, String, String) -> Unit,
    onCrearReserva: (String, String, String, String, String, (Boolean) -> Unit) -> Unit,
    onConfirmarRetiro: (String) -> Unit,
    onRegistrarDevolucion: (String, String, String) -> Unit
) {
    // Controla que pantalla está abierta y como funciona Atras
    val navController = rememberNavController()

    // Inicio queda como ruta inicial de la aplicación.
    NavHost(navController = navController, startDestination = Routes.INICIO) {
        // Las tarjetas del menú llevan a las funciones principales segun el rol.
        composable(Routes.INICIO) {
            HomeScreen(
                onDocenteClick = { navController.navigate(Routes.INVENTARIO) },
                onCraClick = { navController.navigate(Routes.REGISTRAR_DEVOLUCION) },
                onAdministracionClick = { navController.navigate(Routes.REPORTES) }
            )
        }
        // Inventario recibe la lista de Room y las acciones de reservar o cambiar pestaña
        composable(Routes.INVENTARIO) {
            InventarioScreen(
                equipos = equipos,
                onBack = { navController.navigateBackSafely() },
                onNavigate = { route -> navController.navigateToBottomTab(route) },
                onReservar = { id -> navController.navigate(Routes.nuevaReserva(id)) }
            )
        }
        // El equipoId viaja en la ruta para saber que equipo se quiere reservar.
        composable(
            route = Routes.NUEVA_RESERVA_PATTERN,
            arguments = listOf(navArgument(Routes.NUEVA_RESERVA_ARG) { type = NavType.StringType })
        ) { backStackEntry ->
            val equipoId = backStackEntry.arguments?.getString(Routes.NUEVA_RESERVA_ARG).orEmpty()
            val equipoSeleccionado = equipos.firstOrNull { it.id == equipoId }
            NuevaReservaScreen(
                equipo = equipoSeleccionado,
                onBack = { navController.navigateBackSafely() },
                onReservaCreada = {
                    navController.navigate(Routes.MIS_RESERVAS) {
                        launchSingleTop = true
                        popUpTo(Routes.INVENTARIO) { inclusive = false }
                    }
                },
                onConfirmar = { docente, curso, fecha, horario, resultado ->
                    onCrearReserva(equipoId, docente, curso, fecha, horario, resultado)
                }
            )
        }
        // Aqui se muestran las reservas y se puede confirmar el retiro
        composable(Routes.MIS_RESERVAS) {
            MisReservasScreen(
                reservas = reservas,
                onBack = { navController.navigateBackSafely() },
                onNavigate = { route -> navController.navigateToBottomTab(route) },
                onConfirmarRetiro = onConfirmarRetiro
            )
        }
        // Esta pantalla sirve al encargado CRA para dar de alta equipos y recibir devoluciones
        composable(Routes.REGISTRAR_DEVOLUCION) {
            RegistrarDevolucionScreen(
                reservas = devolucionesPendientes,
                onBack = { navController.navigateBackSafely() },
                onAgregarEquipo = onAgregarEquipo,
                onConfirmarDevolucion = onRegistrarDevolucion
            )
        }
        // Los reportes usan las listas reales que vienen de la base de datos
        composable(Routes.REPORTES) {
            ReportesScreen(equipos = equipos, reservas = reservas, onBack = { navController.popBackStack() })
        }
    }
}


/**
 * Maneja la flecha de retorno sin permitir que una pulsación repetida
 * elimine la pantalla Inicio de la pila de navegación, solia quedarse dando vueltas
 */
// esta función evita que la flecha siga retrocediendo cuando ya estamos en Inicio
private fun NavController.navigateBackSafely() {
    when (currentDestination?.route) {
        null, Routes.INICIO -> Unit
        Routes.NUEVA_RESERVA_PATTERN -> navigateToBottomTab(Routes.INVENTARIO)
        else -> navigateToHomeSafely()
    }
}

/**
 * Vuelve al menu principal y elimina las pantallas que estén por encima
 * launchSingleTop evita crear otra instancia de Inicio
 */
// Vuelve al menu y saca las pantallas que quedaron encima de Inicio
private fun NavController.navigateToHomeSafely() {
    if (currentDestination?.route == Routes.INICIO) return

    navigate(Routes.INICIO) {
        popUpTo(Routes.INICIO) {
            inclusive = false
        }
        launchSingleTop = true
    }
}

/**
 * Mantiene Inicio como base y solo una pestaña principal encima
 * No guarda/restaura entradas antiguas del back stack: en esta app no es
 * necesario y evita que estados de navegación previos se reintroduzcan.
 */
// Al cambiar de pestaña dejamos Inicio como base para no acumular pantallas repetidas
private fun NavController.navigateToBottomTab(route: String) {
    if (currentDestination?.route == route) return

    navigate(route) {
        popUpTo(Routes.INICIO) {
            inclusive = false
        }
        launchSingleTop = true
    }
}
