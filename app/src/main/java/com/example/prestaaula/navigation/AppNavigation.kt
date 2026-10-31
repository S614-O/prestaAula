package com.example.prestaaula.navigation

// Aqui observamos los estados y conectamos las acciones de las pantallas con sus ViewModels

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import com.example.prestaaula.ui.theme.ThemeMode
import com.example.prestaaula.ui.viewmodel.LoginViewModel
import com.example.prestaaula.ui.viewmodel.PrestaAulaViewModel

@Composable
fun AppNavigation(
    themeMode: ThemeMode,
    onThemeModeChange: (ThemeMode) -> Unit
) {
    // Este ViewModel contiene los datos de equipos y reservas que usa la app
    val viewModel: PrestaAulaViewModel = viewModel(factory = PrestaAulaViewModel.Factory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Este otro ViewModel revisa el acceso segun el rol elegido
    val loginViewModel: LoginViewModel = viewModel()
    val loginUiState by loginViewModel.uiState.collectAsStateWithLifecycle()

    NavGraph(
        uiState = uiState,
        loginUiState = loginUiState,
        onActualizarUsuarioLogin = loginViewModel::actualizarUsuario,
        onActualizarClaveLogin = loginViewModel::actualizarClave,
        // Esta funcion inicia la corrutina que revisa usuario y clave
        onValidarAcceso = loginViewModel::validarAcceso,
        onLimpiarLogin = loginViewModel::limpiarFormulario,
        onIniciarFormularioReserva = viewModel::iniciarFormularioReserva,
        onActualizarDocente = viewModel::actualizarDocente,
        onActualizarCurso = viewModel::actualizarCurso,
        onSeleccionarFecha = viewModel::seleccionarFecha,
        onActualizarHorario = viewModel::actualizarHorario,
        onCrearReserva = viewModel::crearReserva,
        onIniciarFormularioEquipo = viewModel::iniciarFormularioEquipo,
        onNombreEquipoChange = viewModel::actualizarNombreEquipo,
        onDescripcionEquipoChange = viewModel::actualizarDescripcionEquipo,
        onCategoriaEquipoChange = viewModel::actualizarCategoriaEquipo,
        onEstadoDevolucionChange = viewModel::actualizarEstadoDevolucion,
        onObservacionesDevolucionChange = viewModel::actualizarObservacionesDevolucion,
        onAgregarEquipo = viewModel::agregarEquipo,
        onConfirmarRetiro = viewModel::confirmarRetiro,
        onRegistrarDevolucion = viewModel::registrarDevolucion,
        onLimpiarMensajes = viewModel::limpiarMensajes,
        themeMode = themeMode,
        onThemeModeChange = onThemeModeChange
    )
}
