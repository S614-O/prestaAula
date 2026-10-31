package com.example.prestaaula.model

// Modelo de reserva que usan las pantallas
// Incluye datos del equipo y el estado del proceso de prestamo
// La idea de estos comentarios es explicar el codigo como apunte de clase


// Representa una reserva lista para mostrarse en las pantallas
data class Reserva(
    val id: String,
    val equipoId: String,
    val equipoNombre: String,
    val docente: String,
    val curso: String,
    val fecha: String,
    val horario: String,
    val estado: String = "RESERVADA",
    val esHoy: Boolean = false,
    val estadoDevolucion: String? = null,
    val observacionesDevolucion: String? = null,
    val creadaEn: Long = 0L,
    val fechaDevolucion: Long? = null
)
