# Microservicio de Tienda de Mascotas


### 🛍️ Gestión de Productos
- `GET /productos` - Obtener todos los productos
- `GET /productos/{id}` - Obtener producto por ID
- `GET /productos/activos` - Obtener productos activos
- `GET /productos/categoria/{categoria}` - Filtrar por categoría
- `GET /productos/tipo/{tipoMascota}` - Filtrar por tipo de mascota
- `GET /productos/marca/{marca}` - Filtrar por marca
- `GET /productos/disponibles` - Productos con stock
- `GET /productos/precio?min={min}&max={max}` - Filtrar por rango de precio
- `GET /productos/buscar/{texto}` - Buscar por nombre
- `POST /productos` - Crear nuevo producto
- `PUT /productos/{id}` - Actualizar producto
- `DELETE /productos/{id}` - Desactivar producto (eliminación lógica)
- `PUT /productos/{id}/activar` - Reactivar producto

### 🧾 Gestión de Ventas
- `GET /ventas` - Obtener todas las ventas
- `GET /ventas/{id}` - Obtener venta por ID
- `GET /ventas/producto/{productoId}` - Ventas por producto
- `GET /ventas/hoy` - Ventas del día actual
- `POST /ventas` - Registrar nueva venta (actualiza stock automáticamente)
- `GET /ventas/mas-vendidos` - Productos más vendidos

### 💰 Reportes de Ganancias
- `GET /ganancias` - Resumen completo de ganancias
- `GET /ganancias/diarias` - Ganancias del día actual
- `GET /ganancias/mensuales` - Ganancias del mes actual
- `GET /ganancias/anuales` - Ganancias del año actual


### Puerto
La aplicación se ejecuta en el puerto `8081`

La aplicación estará disponible en: `http://localhost:8081`

