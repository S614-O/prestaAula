package com.example.prestaaula.model

// Modelo simple que usa la interfaz para mostrar un equipo
// No es la tabla de Room sino el objeto que mostramos en pantalla


// Los estados posibles estan definidos aqui para evitar escribirlos de mil formas
enum class EstadoEquipo(val etiqueta: String) {
    DISPONIBLE("DISPONIBLE"),
    PRESTADO("PRESTADO"),
    MANTENCION("EN MANTENCIÓN")
}

// Modelo que recibe la pantalla de inventario
data class Equipo(
    val id: String,
    val nombre: String,
    val descripcion: String,
    val categoria: String,
    val estado: EstadoEquipo
)
