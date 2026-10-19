package com.example.prestaaula.model

enum class EstadoEquipo(val etiqueta: String) {
    DISPONIBLE("DISPONIBLE"),
    PRESTADO("PRESTADO"),
    MANTENCION("EN MANTENCIÓN")
}

data class Equipo(
    val id: String,
    val nombre: String,
    val descripcion: String,
    val categoria: String,
    val estado: EstadoEquipo
)
