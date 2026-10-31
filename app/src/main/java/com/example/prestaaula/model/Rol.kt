package com.example.prestaaula.model

// Lista de los roles que se pueden elegir al entrar a PrestaAula
// La idea de estos comentarios es explicar el codigo como apunte de clase


// Cada rol tiene el texto que vera el usuario y una descripcion corta
enum class Rol(val titulo: String, val descripcion: String) {
    DOCENTE("Soy Docente", "Revisa equipos y solicita un préstamo"),
    ENCARGADO_CRA("Soy Encargado(a) CRA", "Gestiona retiros y devoluciones"),
    ADMINISTRACION("UTP / Administración", "Consulta reportes y actividad")
}
