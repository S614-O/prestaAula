package com.example.prestaaula.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.prestaaula.model.Reserva
import com.example.prestaaula.ui.screens.HomeScreen
import com.example.prestaaula.ui.screens.InventarioScreen
import com.example.prestaaula.ui.screens.MisReservasScreen
import com.example.prestaaula.ui.screens.NuevaReservaScreen
import com.example.prestaaula.ui.screens.RegistrarDevolucionScreen
import com.example.prestaaula.ui.screens.ReportesScreen

@Composable
fun NavGraph(
    reservas: List<Reserva>,
    onConfirmarReserva: (equipoId: String, equipoNombre: String, docente: String, curso: String, fecha: String, horario: String) -> Unit,
    onConfirmarRetiro: (reservaId: String) -> Unit
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.INICIO) {
        composable(Routes.INICIO) {
            HomeScreen(
                onDocenteClick = { navController.navigate(Routes.INVENTARIO) },
                onCraClick = { navController.navigate(Routes.REGISTRAR_DEVOLUCION) },
                onAdministracionClick = { navController.navigate(Routes.REPORTES) }
            )
        }
        composable(Routes.INVENTARIO) {
            InventarioScreen(
                onBack = { navController.popBackStack() },
                onNavigate = { route -> navController.navigate(route) },
                onReservarEquipo = { equipoId -> navController.navigate(Routes.nuevaReserva(equipoId)) }
            )
        }
        composable(
            route = Routes.NUEVA_RESERVA_PATTERN,
            arguments = listOf(navArgument(Routes.NUEVA_RESERVA_ARG) { type = NavType.StringType })
        ) { backStackEntry ->
            val equipoId = backStackEntry.arguments?.getString(Routes.NUEVA_RESERVA_ARG).orEmpty()
            NuevaReservaScreen(
                equipoId = equipoId,
                onBack = { navController.popBackStack() },
                onConfirmar = { equipoNombre, docente, curso, fecha, horario ->
                    onConfirmarReserva(equipoId, equipoNombre, docente, curso, fecha, horario)
                    navController.navigate(Routes.MIS_RESERVAS) {
                        launchSingleTop = true
                        popUpTo(Routes.INVENTARIO) { inclusive = false }
                    }
                }
            )
        }
        composable(Routes.MIS_RESERVAS) {
            MisReservasScreen(
                reservas = reservas,
                onBack = { navController.popBackStack() },
                onNavigate = { route -> navController.navigate(route) },
                onConfirmarRetiro = onConfirmarRetiro
            )
        }
        composable(Routes.REGISTRAR_DEVOLUCION) {
            RegistrarDevolucionScreen(onBack = { navController.popBackStack() })
        }
        composable(Routes.REPORTES) {
            ReportesScreen(onBack = { navController.popBackStack() })
        }
    }
}
