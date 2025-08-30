package mascotas.mascotas.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, Object> home() {
        return Map.of(
            "mensaje", "Bienvenido al Microservicio de Mascotas",
            "version", "1.0.0",
            "endpoints", Map.of(
                "productos", "/mascotas",
                "producto_por_id", "/mascotas/{id}",
                "productos_por_categoria", "/mascotas/categoria/{categoria}",
                "productos_disponibles", "/mascotas/disponibles"
            )
        );
    }

    // Eliminamos el conflicto de ruta /mascotas
    @GetMapping("/info")
    public Map<String, Object> info() {
        return Map.of(
            "mensaje", "Información del microservicio",
            "descripcion", "Los productos están disponibles en /mascotas",
            "total_productos", 8
        );
    }
}
