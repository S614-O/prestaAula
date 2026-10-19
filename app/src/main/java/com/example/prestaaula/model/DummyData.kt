package com.example.prestaaula.model

object DummyData {
    val equipos = listOf(
        Equipo(
            "NB-01",
            "Notebook #01",
            "Lenovo ThinkPad · 14 pulgadas",
            "NOTEBOOKS",
            EstadoEquipo.DISPONIBLE
        ),
        Equipo(
            "NB-03",
            "Notebook #03",
            "HP ProBook · 15 pulgadas",
            "NOTEBOOKS",
            EstadoEquipo.PRESTADO
        ),
        Equipo(
            "NB-07",
            "Notebook #07",
            "Dell Latitude · 14 pulgadas",
            "NOTEBOOKS",
            EstadoEquipo.DISPONIBLE
        ),
        Equipo(
            "NB-09",
            "Notebook #09",
            "Lenovo ThinkPad · revisión técnica",
            "NOTEBOOKS",
            EstadoEquipo.MANTENCION
        ),
        Equipo(
            "PR-01",
            "Proyector #01",
            "Epson PowerLite · HDMI",
            "PROYECTORES",
            EstadoEquipo.DISPONIBLE
        ),
        Equipo(
            "PR-02",
            "Proyector #02",
            "ViewSonic · HDMI / VGA",
            "PROYECTORES",
            EstadoEquipo.PRESTADO
        ),
        Equipo(
            "PR-04",
            "Proyector #04",
            "Epson PowerLite · lámpara en revisión",
            "PROYECTORES",
            EstadoEquipo.MANTENCION
        )
    )

    val reservas = listOf(
        Reserva(
            "R-001",
            "NB-03",
            "Notebook #03",
            "Prof. Lazo",
            "3° Medio B",
            "19-10-2026",
            "08:00 - 08:45",
            esHoy = true
        ),
        Reserva(
            "R-002",
            "PR-01",
            "Proyector #01",
            "Prof. Soto",
            "2° Medio A",
            "20-10-2026",
            "09:45 - 10:30"
        ),
        Reserva(
            "R-003",
            "NB-07",
            "Notebook #07",
            "Prof. Adrovez",
            "3° Medio B",
            "22-10-2026",
            "11:30 - 12:15"
        )
    )

    val equiposConIncidencias = listOf(
        Reporte("Notebook #09", "3 incidencias · batería y carga", "Último registro: 16-10-2026"),
        Reporte("Proyector #04", "2 incidencias · lámpara", "Último registro: 14-10-2026"),
        Reporte("Notebook #03", "1 incidencia · cargador", "Último registro: 09-10-2026")
    )

    val historialReciente = listOf(
        Reporte("Devolución registrada", "Notebook #02 · Prof. Cantero", "Hoy · 12:40"),
        Reporte("Reserva creada", "Proyector #01 · 2° Medio A", "Hoy · 10:15"),
        Reporte("Equipo enviado a mantención", "Notebook #09", "16-10-2026 · 15:20")
    )
}
