package com.example.prestaaula.navigation
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prestaaula.ui.viewmodel.PrestaAulaViewModel
import androidx.compose.runtime.getValue

@Composable
// Este composable prepara los datos y las acciones antes de mostrarlos en NavGraph
fun AppNavigation() {
    // El ViewModel se comparte durante la navegación para no recrear toda la lógica en cada pantalla
    val viewModel: PrestaAulaViewModel = viewModel(factory = PrestaAulaViewModel.Factory)
    // collectAsStateWithLifecycle observa los datos respetando el ciclo de vida de Android
    val equipos by viewModel.equipos.collectAsStateWithLifecycle()
    val reservas by viewModel.reservas.collectAsStateWithLifecycle()
    val devolucionesPendientes by viewModel.devolucionesPendientes.collectAsStateWithLifecycle()

    // Se pasan listas ya observadas y funciones para que las pantallas pidan acciones
    NavGraph(
        equipos = equipos,
        reservas = reservas,
        devolucionesPendientes = devolucionesPendientes,
        onAgregarEquipo = viewModel::agregarEquipo,
        onCrearReserva = viewModel::crearReserva,
        onConfirmarRetiro = viewModel::confirmarRetiro,
        onRegistrarDevolucion = viewModel::registrarDevolucion
    )
}
