package com.example.prestaaula.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import com.example.prestaaula.model.DummyData
import com.example.prestaaula.model.Reserva

@Composable
fun AppNavigation() {
    val reservas = remember {
        mutableStateListOf<Reserva>().apply { addAll(DummyData.reservas) }
    }

    NavGraph(
        reservas = reservas,
        onConfirmarReserva = { equipoId, equipoNombre, docente, curso, fecha, horario ->
            val numero = (reservas.size + 1).toString().padStart(3, '0')
            reservas.add(
                Reserva(
                    id = "R-$numero",
                    equipoId = equipoId,
                    equipoNombre = equipoNombre,
                    docente = docente.trim(),
                    curso = curso,
                    fecha = fecha.trim(),
                    horario = horario.substringBefore(" ("),
                    esHoy = fecha.trim() == "19-10-2026"
                )
            )
        },
        onConfirmarRetiro = { reservaId ->
            val index = reservas.indexOfFirst { it.id == reservaId }
            if (index >= 0) reservas[index] = reservas[index].copy(estado = "RETIRADA")
        }
    )
}
