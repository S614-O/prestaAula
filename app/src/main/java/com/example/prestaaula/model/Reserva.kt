package com.example.prestaaula.model

data class Reserva(
    val id: String,
    val equipoId: String,
    val equipoNombre: String,
    val docente: String,
    val curso: String,
    val fecha: String,
    val horario: String,
    val estado: String = "RESERVADA",
    val esHoy: Boolean = false
)
