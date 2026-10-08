# FleetControl API

API REST para el control de una flota de vehículos: vehículos, mantenimientos y repostajes, con autenticación mediante JWT.

## Stack

- Java 21
- Spring Boot 3 (Web, Data JPA, Validation, Security)
- PostgreSQL 16
- Flyway (migraciones)
- Maven
- Docker y Docker Compose

## Requisitos funcionales

- Un usuario puede registrarse e iniciar sesión.
- Un usuario autenticado puede crear, ver, editar y borrar sus vehículos (matrícula, marca, modelo, año, kilometraje, estado).
- Cada vehículo puede tener mantenimientos (tipo, fecha, coste, kilometraje en el servicio, notas).
- Cada vehículo puede tener repostajes (fecha, litros, precio total, kilometraje).
- El estado de un vehículo es `AVAILABLE`, `IN_USE` o `IN_MAINTENANCE`.
- El listado de vehículos se puede filtrar por estado y paginar.
- Se pueden consultar estadísticas de un vehículo (consumo medio y coste total).

## Reglas de negocio

- La matrícula es única.
- El kilometraje de un vehículo nunca puede disminuir.

## Requisitos no funcionales

- Las contraseñas nunca se guardan en texto plano.
- Los errores devuelven un JSON con formato uniforme.
- Tests en servicios y controladores.
- La aplicación y la base de datos arrancan con Docker Compose.

## Modelo de datos

```
users        (id, email UNIQUE, password_hash, role, created_at)
vehicles     (id, plate UNIQUE, brand, model, year, mileage, status, owner_id -> users.id, created_at)
maintenances (id, type, date, cost, mileage_at_service, notes, vehicle_id -> vehicles.id)
fuel_logs    (id, date, liters, total_price, mileage, vehicle_id -> vehicles.id)
```

Un usuario tiene muchos vehículos. Un vehículo tiene muchos mantenimientos y muchos repostajes.

## Endpoints

| Método | Ruta | Descripción | Éxito |
|--------|------|-------------|-------|
| POST | `/api/auth/register` | Registro | 201 |
| POST | `/api/auth/login` | Login, devuelve JWT | 200 |
| GET | `/api/vehicles?status=AVAILABLE&page=0&size=10` | Listar vehículos | 200 |
| POST | `/api/vehicles` | Crear vehículo | 201 |
| GET | `/api/vehicles/{id}` | Ver vehículo | 200 |
| PUT | `/api/vehicles/{id}` | Editar vehículo | 200 |
| DELETE | `/api/vehicles/{id}` | Borrar vehículo | 204 |
| GET | `/api/vehicles/{id}/maintenances` | Listar mantenimientos | 200 |
| POST | `/api/vehicles/{id}/maintenances` | Registrar mantenimiento | 201 |
| DELETE | `/api/maintenances/{id}` | Borrar mantenimiento | 204 |
| GET | `/api/vehicles/{id}/fuel-logs` | Listar repostajes | 200 |
| POST | `/api/vehicles/{id}/fuel-logs` | Registrar repostaje | 201 |
| GET | `/api/vehicles/{id}/stats` | Consumo medio y coste total | 200 |

## Estructura del proyecto

```
com.example.fleetcontrol
├── config        configuración (seguridad, OpenAPI)
├── controller    capa web: recibe HTTP, sin lógica de negocio
├── service       lógica de negocio
├── repository    acceso a datos
├── model         entidades JPA
├── dto           objetos de entrada y salida
├── exception     excepciones propias y manejador global
└── security      JWT y filtros
```

Regla de capas: el controller solo habla con el service, y el service con el repository.

## Cómo ejecutarlo en local

1. Levantar la base de datos:

```bash
docker compose up -d
```

2. Arrancar la aplicación:

```bash
./mvnw spring-boot:run
```

En PowerShell (Windows):

```powershell
.\mvnw spring-boot:run
```

La API queda en `http://localhost:8080`.

## Estado del proyecto

- [x] Esqueleto del proyecto y base de datos con Docker
- [ ] Migraciones y entidades
- [ ] Servicios y controladores
- [ ] Validación y manejo de errores
- [ ] Tests
- [ ] Seguridad con JWT
- [ ] Documentación con Swagger
- [ ] Dockerfile y CI