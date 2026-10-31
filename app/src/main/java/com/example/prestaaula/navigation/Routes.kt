package com.example.prestaaula.navigation

import com.example.prestaaula.model.Rol

// Nombres de las rutas para no escribir las mismas cadenas en varios archivos
object Routes {
    const val INICIO = "inicio"
    const val LOGIN_PATTERN = "login/{rol}"
    const val LOGIN_ARG = "rol"
    const val INVENTARIO = "inventario"
    const val NUEVA_RESERVA_PATTERN = "nueva_reserva/{equipoId}"
    const val NUEVA_RESERVA_ARG = "equipoId"
    const val MIS_RESERVAS = "mis_reservas"
    const val REGISTRAR_DEVOLUCION = "registrar_devolucion"
    const val REPORTES = "reportes"

    // La ruta lleva el rol para que el login sepa a quien esta dando acceso
    fun login(rol: Rol): String = "login/${rol.name}"

    // El ID se agrega a la ruta para que el formulario reciba el equipo elegido
    fun nuevaReserva(equipoId: String): String = "nueva_reserva/$equipoId"
}
