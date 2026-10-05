# API de Empleados · Spring Boot + PostgreSQL

API REST para gestionar empleados con un CRUD completo. La hice para practicar DTOs, mappers, validaciones y manejo global de errores.

El proyecto lo construí en 4 etapas, y cada una tiene su commit: primero un CRUD sin DTOs, y después fui agregando DTOs, mapper, excepciones y validaciones. En el historial de commits se ve cómo cada etapa resuelve los problemas de la anterior.

## Tecnologías

| Tecnología | Uso |
|------------|-----|
| Java 21 | Lenguaje |
| Spring Boot | Framework principal |
| Spring Data JPA | Acceso a la base de datos |
| Spring Validation | Validación de datos de entrada |
| PostgreSQL | Base de datos |
| Lombok | Menos código repetido |

## Estructura

```
src/main/java/com/ejemplo/empleados/
├── entity/        # Empleado: cómo se guarda en la base de datos
├── repository/    # Acceso a la base de datos
├── dto/           # EmpleadoRequest (entrada) y EmpleadoResponse (salida)
├── mapper/        # Conversión entre entidad y DTOs
├── exception/     # Excepciones propias y manejo global de errores
├── service/       # Reglas del negocio
└── controller/    # Endpoints REST
```

## Cómo viajan los datos

```
JSON → EmpleadoRequest → (mapper) → Empleado → BD
                                       │
JSON ← EmpleadoResponse ← (mapper) ────┘
```

- **EmpleadoRequest** no tiene `id`, `activo` ni `fechaRegistro`, así el cliente no puede sobrescribir registros ni tocar campos internos.
- **EmpleadoResponse** no muestra el campo interno `activo` y agrega `nombreCompleto`.

## Cómo ejecutarlo

**Requisitos:** Java 21 y PostgreSQL (puerto 5432).

**1. Crear la base de datos:**

```sql
CREATE DATABASE empleados_db;
```

**2. Revisar las credenciales** en `src/main/resources/application.properties`. Por defecto usa `postgres` / `postgres`.

**3. Ejecutar:**

```bash
git clone https://github.com/ederjesus1004/api-empleados.git
cd api-empleados
./mvnw spring-boot:run
```

En Windows usa `mvnw.cmd spring-boot:run`. La API queda en `http://localhost:8080` y la tabla se crea sola.

## Endpoints

| Método | Ruta | Descripción | Respuesta |
|--------|------|-------------|-----------|
| GET | `/api/empleados` | Lista los empleados | 200 |
| GET | `/api/empleados/{id}` | Obtiene un empleado | 200 / 404 |
| POST | `/api/empleados` | Registra un empleado | 201 / 400 / 409 |
| PUT | `/api/empleados/{id}` | Edita un empleado | 200 / 400 / 404 / 409 |
| DELETE | `/api/empleados/{id}` | Elimina un empleado | 204 / 404 |

## Ejemplo

`POST /api/empleados`

```json
{
  "nombre": "Juan",
  "apellido": "Pérez",
  "dni": "12345678",
  "cargo": "Desarrollador",
  "sueldo": 3500.00
}
```

Respuesta `201`:

```json
{
  "id": 1,
  "nombreCompleto": "Juan Pérez",
  "dni": "12345678",
  "cargo": "Desarrollador",
  "sueldo": 3500.00,
  "fechaRegistro": "2026-10-05T11:20:15"
}
```

Respuesta `400` con datos inválidos:

```json
{
  "fecha": "2026-10-05T11:21:02",
  "estado": 400,
  "mensaje": "Datos inválidos",
  "errores": {
    "nombre": "El nombre es obligatorio",
    "dni": "El DNI debe tener exactamente 8 dígitos"
  }
}
```

## Validaciones

| Campo | Regla |
|-------|-------|
| nombre | Obligatorio, máximo 100 caracteres |
| apellido | Obligatorio, máximo 100 caracteres |
| dni | Obligatorio, exactamente 8 dígitos y único |
| cargo | Obligatorio, máximo 50 caracteres |
| sueldo | Obligatorio y mayor a 0 |

## Códigos de respuesta

| Código | Cuándo sale |
|--------|-------------|
| 200 | Consulta o edición correcta |
| 201 | Empleado registrado |
| 204 | Empleado eliminado |
| 400 | Datos inválidos o JSON mal escrito |
| 404 | El empleado no existe |
| 409 | El DNI ya lo tiene otro empleado |

## Lo que aprendí

- Para qué sirve un DTO: evita que el cliente mande campos que no debe y controla lo que se muestra
- Por qué se usa un mapper: la conversión vive en un solo lugar y no se repite
- Cómo crear excepciones propias y responderlas con el código HTTP correcto desde `@RestControllerAdvice`
- Que las validaciones van en el DTO de entrada y se activan con `@Valid`

## Autor

**Eder Cuaresma** · Estudiante de Ingeniería de Sistemas e Informática, UTP
GitHub: [@ederjesus1004](https://github.com/ederjesus1004)
