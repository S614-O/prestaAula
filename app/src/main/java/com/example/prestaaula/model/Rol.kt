package com.example.prestaaula.model

enum class Rol(val titulo: String, val descripcion: String) {
    DOCENTE("Soy Docente", "Revisa equipos y solicita un préstamo"),
    ENCARGADO_CRA("Soy Encargado(a) CRA", "Gestiona retiros y devoluciones"),
    ADMINISTRACION("UTP / Administración", "Consulta reportes y actividad")
}
