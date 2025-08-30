# Proyecto Mascotas - Microservicio

## Descripción
Microservicio básico para una tienda de mascotas.

## Características
- Framework: Spring Boot 3.5.5
- Java: 17
- API REST para gestión de productos
- Datos almacenados en memoria

## Endpoints disponibles
- `GET /mascotas` - Obtiene todos los productos
- `GET /mascotas/{id}` - Obtiene un producto por ID
- `GET /mascotas/categoria/{categoria}` - Productos por categoría
- `GET /mascotas/disponibles` - Productos con stock disponible

## Cómo ejecutar
```bash
./mvnw spring-boot:run
```

El servidor se iniciará en http://localhost:8080
