package mascotas.mascotas.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, Object> home() {
        return Map.of(
            "mensaje", "Microservicio de Gestión de Productos para Mascotas",
            "version", "1.0.0",
            "descripcion", "API RESTful para gestión completa de productos",
            "base_datos", "Oracle Cloud Database",
            "puerto", "8081",
            "endpoints_principales", Map.of(
                "productos", "/productos",
                "producto_por_id", "/productos/{id}",
                "productos_activos", "/productos/activos",
                "por_categoria", "/productos/categoria/{categoria}",
                "por_tipo_mascota", "/productos/tipo/{tipoMascota}",
                "productos_disponibles", "/productos/disponibles"
            ),
            "operaciones_crud", Map.of(
                "crear", "POST /productos",
                "leer", "GET /productos",
                "actualizar", "PUT /productos/{id}",
                "eliminar", "DELETE /productos/{id}"
            )
        );
    }

    @GetMapping("/health")
    public Map<String, Object> health() {
        return Map.of(
            "status", "UP",
            "microservicio", "mascotas-productos",
            "base_datos", "Oracle Cloud conectado",
            "puerto", "8081"
        );
    }
}
