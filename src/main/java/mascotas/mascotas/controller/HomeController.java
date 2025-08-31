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
                "productos_disponibles", "/mascotas/disponibles",
                "ganancias_todas", "/ganancias",
                "ganancias_diarias", "/ganancias/diarias",
                "ganancias_mensuales", "/ganancias/mensuales", 
                "ganancias_anuales", "/ganancias/anuales"
            )
        );
    }

    @GetMapping("/info")
    public Map<String, Object> info() {
        return Map.of(
            "mensaje", "Información del microservicio",
            "descripcion", "Microservicio para tienda de mascotas con gestión de productos y ganancias",
            "funcionalidades", Map.of(
                "productos", "Gestión productos en memoria",
                "ganancias", "Cálculo de ganancias diarias, mensuales y anuales"
            )
        );
    }
}
