package com.example.prestaaula.navigation

// Aqui conectamos las rutas con las pantallas
// Tambien revisamos que cada rol llegue a su lugar despues del login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.prestaaula.model.Rol
import com.example.prestaaula.ui.screens.HomeScreen
import com.example.prestaaula.ui.screens.InventarioScreen
import com.example.prestaaula.ui.screens.LoginScreen
import com.example.prestaaula.ui.screens.MisReservasScreen
import com.example.prestaaula.ui.screens.NuevaReservaScreen
import com.example.prestaaula.ui.screens.RegistrarDevolucionScreen
import com.example.prestaaula.ui.screens.ReportesScreen
import com.example.prestaaula.ui.theme.ThemeMode
import com.example.prestaaula.ui.viewmodel.LoginUiState
import com.example.prestaaula.ui.viewmodel.PrestaAulaUiState

@Composable
fun NavGraph(
    uiState: PrestaAulaUiState,
    loginUiState: LoginUiState,
    onActualizarUsuarioLogin: (String) -> Unit,
    onActualizarClaveLogin: (String) -> Unit,
    onValidarAcceso: (Rol) -> Unit,
    onLimpiarLogin: () -> Unit,
    onIniciarFormularioReserva: (String) -> Unit,
    onActualizarDocente: (String) -> Unit,
    onActualizarCurso: (String) -> Unit,
    onSeleccionarFecha: (Long) -> Unit,
    onActualizarHorario: (String) -> Unit,
    onCrearReserva: ((Boolean) -> Unit) -> Unit,
    onIniciarFormularioEquipo: () -> Unit,
    onNombreEquipoChange: (String) -> Unit,
    onDescripcionEquipoChange: (String) -> Unit,
    onCategoriaEquipoChange: (String) -> Unit,
    onEstadoDevolucionChange: (String, String) -> Unit,
    onObservacionesDevolucionChange: (String, String) -> Unit,
    onAgregarEquipo: ((Boolean) -> Unit) -> Unit,
    onConfirmarRetiro: (String) -> Unit,
    onRegistrarDevolucion: (String) -> Unit,
    onLimpiarMensajes: () -> Unit,
    themeMode: ThemeMode,
    onThemeModeChange: (ThemeMode) -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.INICIO,
        // Dejamos un efecto suave por defecto para cualquier ruta nueva
        enterTransition = { fadeIn(animationSpec = tween(250)) },
        exitTransition = { fadeOut(animationSpec = tween(250)) },
        popEnterTransition = { fadeIn(animationSpec = tween(250)) },
        popExitTransition = { fadeOut(animationSpec = tween(250)) }
    ) {
        composable(
            route = Routes.INICIO,
            enterTransition = { entrarDesdeDerecha() },
            exitTransition = { salirHaciaIzquierda() },
            popEnterTransition = { volverDesdeIzquierda() },
            popExitTransition = { salirHaciaDerecha() }
        ) {
            HomeScreen(
                themeMode = themeMode,
                onThemeModeChange = onThemeModeChange,
                onDocenteClick = { navController.navigate(Routes.login(Rol.DOCENTE)) },
                onCraClick = { navController.navigate(Routes.login(Rol.ENCARGADO_CRA)) },
                onAdministracionClick = { navController.navigate(Routes.login(Rol.ADMINISTRACION)) }
            )
        }

        // La ruta recibe el rol y por eso cambia el titulo y los datos de prueba del login
        composable(
            route = Routes.LOGIN_PATTERN,
            arguments = listOf(navArgument(Routes.LOGIN_ARG) { type = NavType.StringType }),
            enterTransition = { entrarDesdeDerecha() },
            exitTransition = { salirHaciaIzquierda() },
            popEnterTransition = { volverDesdeIzquierda() },
            popExitTransition = { salirHaciaDerecha() }
        ) { backStackEntry ->
            val rolTexto = backStackEntry.arguments?.getString(Routes.LOGIN_ARG).orEmpty()
            val rolSeleccionado = Rol.values().firstOrNull { it.name == rolTexto } ?: Rol.DOCENTE

            LaunchedEffect(rolSeleccionado) {
                onLimpiarLogin()
            }

            // Cuando el ViewModel termina la corrutina y confirma el acceso navegamos segun el rol
            LaunchedEffect(loginUiState.accesoPermitido) {
                if (loginUiState.accesoPermitido) {
                    val destino = when (rolSeleccionado) {
                        Rol.DOCENTE -> Routes.INVENTARIO
                        Rol.ENCARGADO_CRA -> Routes.REGISTRAR_DEVOLUCION
                        Rol.ADMINISTRACION -> Routes.REPORTES
                    }

                    navController.navigate(destino) {
                        popUpTo(Routes.INICIO) { inclusive = false }
                        launchSingleTop = true
                    }
                    onLimpiarLogin()
                }
            }

            LoginScreen(
                rol = rolSeleccionado,
                uiState = loginUiState,
                onUsuarioChange = onActualizarUsuarioLogin,
                onClaveChange = onActualizarClaveLogin,
                onVolver = {
                    onLimpiarLogin()
                    navController.navigateToHomeSafely()
                },
                onIngresar = { onValidarAcceso(rolSeleccionado) }
            )
        }

        composable(
            route = Routes.INVENTARIO,
            enterTransition = { entrarDesdeDerecha() },
            exitTransition = { salirHaciaIzquierda() },
            popEnterTransition = { volverDesdeIzquierda() },
            popExitTransition = { salirHaciaDerecha() }
        ) {
            InventarioScreen(
                notebooks = uiState.notebooks,
                proyectores = uiState.proyectores,
                otrosEquipos = uiState.otrosEquipos,
                cargando = uiState.cargando,
                mensajeError = uiState.mensajeError,
                onDismissMessage = onLimpiarMensajes,
                onBack = { navController.navigateBackSafely() },
                onNavigate = { route -> navController.navigateToBottomTab(route) },
                onReservar = { id -> navController.navigate(Routes.nuevaReserva(id)) }
            )
        }

        composable(
            route = Routes.NUEVA_RESERVA_PATTERN,
            arguments = listOf(navArgument(Routes.NUEVA_RESERVA_ARG) { type = NavType.StringType }),
            enterTransition = { entrarDesdeDerecha() },
            exitTransition = { salirHaciaIzquierda() },
            popEnterTransition = { volverDesdeIzquierda() },
            popExitTransition = { salirHaciaDerecha() }
        ) { backStackEntry ->
            val equipoId = backStackEntry.arguments?.getString(Routes.NUEVA_RESERVA_ARG).orEmpty()
            val equipoSeleccionado = uiState.equipos.firstOrNull { it.id == equipoId }

            // Preparamos los campos una sola vez cuando se abre el formulario
            LaunchedEffect(equipoId) {
                onIniciarFormularioReserva(equipoId)
            }

            NuevaReservaScreen(
                equipo = equipoSeleccionado,
                formulario = uiState.formularioReserva,
                cargandoDatos = uiState.cargando,
                operacionEnCurso = uiState.operacionEnCurso,
                mensajeError = uiState.mensajeError,
                onDismissMessage = onLimpiarMensajes,
                onDocenteChange = onActualizarDocente,
                onCursoChange = onActualizarCurso,
                onFechaSeleccionada = onSeleccionarFecha,
                onHorarioChange = onActualizarHorario,
                onBack = { navController.navigateBackSafely() },
                onReservaCreada = {
                    navController.navigate(Routes.MIS_RESERVAS) {
                        launchSingleTop = true
                        popUpTo(Routes.INVENTARIO) { inclusive = false }
                    }
                },
                onConfirmar = onCrearReserva
            )
        }

        composable(
            route = Routes.MIS_RESERVAS,
            enterTransition = { entrarDesdeDerecha() },
            exitTransition = { salirHaciaIzquierda() },
            popEnterTransition = { volverDesdeIzquierda() },
            popExitTransition = { salirHaciaDerecha() }
        ) {
            MisReservasScreen(
                reservasHoy = uiState.reservasHoy,
                reservasProximas = uiState.reservasProximas,
                reservasDevueltas = uiState.reservasDevueltas,
                cargando = uiState.cargando,
                operacionEnCurso = uiState.operacionEnCurso,
                mensajeError = uiState.mensajeError,
                mensajeExito = uiState.mensajeExito,
                onDismissMessage = onLimpiarMensajes,
                onBack = { navController.navigateBackSafely() },
                onNavigate = { route -> navController.navigateToBottomTab(route) },
                onConfirmarRetiro = onConfirmarRetiro
            )
        }

        composable(
            route = Routes.REGISTRAR_DEVOLUCION,
            enterTransition = { entrarDesdeDerecha() },
            exitTransition = { salirHaciaIzquierda() },
            popEnterTransition = { volverDesdeIzquierda() },
            popExitTransition = { salirHaciaDerecha() }
        ) {
            RegistrarDevolucionScreen(
                reservas = uiState.devolucionesPendientes,
                cargandoDatos = uiState.cargando,
                formularioEquipo = uiState.formularioEquipo,
                formulariosDevolucion = uiState.formulariosDevolucion,
                operacionEnCurso = uiState.operacionEnCurso,
                mensajeError = uiState.mensajeError,
                mensajeExito = uiState.mensajeExito,
                onDismissMessage = onLimpiarMensajes,
                onIniciarFormularioEquipo = onIniciarFormularioEquipo,
                onNombreEquipoChange = onNombreEquipoChange,
                onDescripcionEquipoChange = onDescripcionEquipoChange,
                onCategoriaEquipoChange = onCategoriaEquipoChange,
                onEstadoDevolucionChange = onEstadoDevolucionChange,
                onObservacionesChange = onObservacionesDevolucionChange,
                onBack = { navController.navigateBackSafely() },
                onAgregarEquipo = onAgregarEquipo,
                onConfirmarDevolucion = onRegistrarDevolucion
            )
        }

        composable(
            route = Routes.REPORTES,
            enterTransition = { entrarDesdeDerecha() },
            exitTransition = { salirHaciaIzquierda() },
            popEnterTransition = { volverDesdeIzquierda() },
            popExitTransition = { salirHaciaDerecha() }
        ) {
            ReportesScreen(
                reportes = uiState.reportes,
                cargando = uiState.cargando,
                mensajeError = uiState.mensajeError,
                onDismissMessage = onLimpiarMensajes,
                onBack = { navController.navigateBackSafely() }
            )
        }
    }
}

// Estas funciones hacen que las pantallas se deslicen como en el ejemplo de clases
// Al avanzar la pantalla nueva entra desde la derecha
private fun AnimatedContentTransitionScope<NavBackStackEntry>.entrarDesdeDerecha(): EnterTransition {
    return slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
        animationSpec = tween(300)
    )
}

// La pantalla que dejamos se mueve hacia la izquierda
private fun AnimatedContentTransitionScope<NavBackStackEntry>.salirHaciaIzquierda(): ExitTransition {
    return slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
        animationSpec = tween(300)
    )
}

// Al usar atras vuelve a entrar la pantalla anterior desde la izquierda
private fun AnimatedContentTransitionScope<NavBackStackEntry>.volverDesdeIzquierda(): EnterTransition {
    return slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Right,
        animationSpec = tween(300)
    )
}

// La pantalla actual sale hacia la derecha cuando se vuelve atras
private fun AnimatedContentTransitionScope<NavBackStackEntry>.salirHaciaDerecha(): ExitTransition {
    return slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Right,
        animationSpec = tween(300)
    )
}

// Evita que la flecha elimine el inicio si ya estamos en el menu
private fun NavController.navigateBackSafely() {
    when (currentDestination?.route) {
        null, Routes.INICIO -> Unit
        Routes.NUEVA_RESERVA_PATTERN -> {
            // En una reserva usamos popBackStack para volver al inventario con la animacion de atras
            if (!popBackStack()) navigateToBottomTab(Routes.INVENTARIO)
        }
        else -> navigateToHomeSafely()
    }
}

// Vuelve al inicio y elimina las pantallas que quedaron encima
private fun NavController.navigateToHomeSafely() {
    if (currentDestination?.route == Routes.INICIO) return

    // Si el inicio sigue en el historial solo volvemos a el
    // Asi se usa la animacion de pop y no agregamos otra pantalla inicio
    val pudoVolver = popBackStack(Routes.INICIO, inclusive = false)
    if (!pudoVolver) {
        navigate(Routes.INICIO) {
            popUpTo(Routes.INICIO) { inclusive = false }
            launchSingleTop = true
        }
    }
}

// Mantiene el inicio como base cuando se cambia entre las dos pestanas
private fun NavController.navigateToBottomTab(route: String) {
    if (currentDestination?.route == route) return
    navigate(route) {
        popUpTo(Routes.INICIO) { inclusive = false }
        launchSingleTop = true
    }
}
