package com.example.prestaaula.navigation

// Nombres de las rutas. Así evitamos escribir las mismas cadenas en muchos archivos.


// Guardamos los nombres de ruta en un solo lugar para no equivocarnos al escribirlos.
object Routes {
    const val INICIO = "inicio"
    const val INVENTARIO = "inventario"
    const val NUEVA_RESERVA_PATTERN = "nueva_reserva/{equipoId}"
    const val NUEVA_RESERVA_ARG = "equipoId"
    const val MIS_RESERVAS = "mis_reservas"
    const val REGISTRAR_DEVOLUCION = "registrar_devolucion"
    const val REPORTES = "reportes"

    // El ID se agrega a la ruta para que el formulario reciba el equipo elegido.
    fun nuevaReserva(equipoId: String): String = "nueva_reserva/$equipoId"
}
