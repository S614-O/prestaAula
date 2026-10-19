package com.example.prestaaula.navigation

object Routes {
    const val INICIO = "inicio"
    const val INVENTARIO = "inventario"
    const val NUEVA_RESERVA_PATTERN = "nueva_reserva/{equipoId}"
    const val NUEVA_RESERVA_ARG = "equipoId"
    const val MIS_RESERVAS = "mis_reservas"
    const val REGISTRAR_DEVOLUCION = "registrar_devolucion"
    const val REPORTES = "reportes"

    fun nuevaReserva(equipoId: String): String = "nueva_reserva/$equipoId"
}
