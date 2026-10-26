package com.example.prestaaula.model

// Modelo pequeño para representar información de un reporte
// La idea de estos comentarios es explicar el codigo como apunte de clase

// Agrupa el titulo, detalle y fecha de un elemento de reporte
data class Reporte(
    val titulo: String,
    val detalle: String,
    val fecha: String
)
