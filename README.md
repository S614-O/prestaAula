# PrestaAula

PrestaAula es una aplicacion Android para apoyar el prestamo de recursos tecnologicos del Colegio Marcela Paz. Permite gestionar equipos y reservas desde tres perfiles: Docente, Encargado CRA y UTP / Administracion.

## Funciones principales

- **Docente:** consulta el inventario, crea reservas y confirma el retiro de equipos.
- **Encargado CRA:** registra equipos y gestiona las devoluciones.
- **UTP / Administracion:** consulta reportes de prestamos e incidencias.
- **Apariencia:** incluye tema claro y oscuro con colores verdes y fuente Terraria.

## Tecnologias

Kotlin · Jetpack Compose · Material 3 · Navigation Compose · Room / SQLite · MVVM · StateFlow · Coroutines
## Como esta organizado

- `model/`: modelos de equipos, reservas y roles.
- `data/`: Repository, entidades, DAO y base de datos Room.
- `navigation/`: rutas y navegacion entre pantallas.
- `ui/screens/`: pantallas de la aplicacion.
- `ui/components/`: componentes reutilizables.
- `ui/theme/`: colores, tipografia y tema.
- `ui/viewmodel/`: estados y logica de las pantallas.

## Navegacion y datos

`NavHost` relaciona cada `route` con su pantalla mediante `composable()`. El `NavController` permite navegar entre rutas y enviar argumentos como `equipoId` al formulario de reserva.

La aplicacion sigue MVVM: las pantallas muestran el estado, los ViewModels manejan las acciones y validaciones, y el Repository trabaja con Room para guardar equipos y reservas localmente.

## Ejecutar el proyecto

1. Descomprimir el proyecto.
2. Abrir la carpeta `prestaAula` desde Android Studio.
3. Sincronizar Gradle.
4. Ejecutar la configuracion `app` en un emulador o dispositivo.

## Accesos de demostracion

| Perfil | Usuario | Clave |
|---|---|---|
| Docente | `docente` | `1234` |
| Encargado CRA | `cra` | `1234` |
| UTP / Administracion | `utp` | `1234` |

## Alcance

Es un proyecto academico. Los accesos son de demostracion y Room guarda los datos solo en el dispositivo. No incluye backend ni sincronizacion entre dispositivos.

**Integrantes: Amaro y Soto**  
Colegio Marcela Paz
