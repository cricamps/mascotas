package mascotas.mascotas.controller;

import mascotas.mascotas.service.TiendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/ganancias")
public class GananciasController {

    @Autowired
    private TiendaService tiendaService;
    
    private final NumberFormat formatoNumero = DecimalFormat.getInstance(new Locale("es", "CL"));
    private final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final DateTimeFormatter formatoFechaHora = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    // Método para obtener nombre del mes en español
    private String obtenerNombreMes(int numeroMes) {
        String[] mesesEspanol = {
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        };
        return mesesEspanol[numeroMes - 1];
    }

    // GET /ganancias/diarias - Ganancias del día actual
    @GetMapping("/diarias")
    public Map<String, Object> obtenerGananciasDiarias() {
        Double ganancias = tiendaService.calcularGananciasDiarias();
        
        return Map.of(
            "periodo", "Diarias",
            "fecha", java.time.LocalDate.now().format(formatoFecha),
            "ganancia", formatoNumero.format(ganancias),
            "moneda", "CLP"
        );
    }

    // GET /ganancias/mensuales - Ganancias del mes actual
    @GetMapping("/mensuales")
    public Map<String, Object> obtenerGananciasMensuales() {
        Double ganancias = tiendaService.calcularGananciasMensuales();
        java.time.LocalDate fechaActual = java.time.LocalDate.now();
        
        return Map.of(
            "periodo", "Mensuales",
            "mes", obtenerNombreMes(fechaActual.getMonthValue()),
            "numeroMes", String.format("%02d", fechaActual.getMonthValue()),
            "año", fechaActual.getYear(),
            "ganancia", formatoNumero.format(ganancias),
            "moneda", "CLP"
        );
    }

    // GET /ganancias/anuales - Ganancias del año actual
    @GetMapping("/anuales")
    public Map<String, Object> obtenerGananciasAnuales() {
        Double ganancias = tiendaService.calcularGananciasAnuales();
        
        return Map.of(
            "periodo", "Anuales",
            "año", java.time.LocalDate.now().getYear(),
            "ganancia", formatoNumero.format(ganancias),
            "moneda", "CLP"
        );
    }

    // GET /ganancias - Resumen de todas las ganancias
    @GetMapping
    public Map<String, Object> obtenerResumenGanancias() {
        return Map.of(
            "ganancias", Map.of(
                "diarias", formatoNumero.format(tiendaService.calcularGananciasDiarias()),
                "mensuales", formatoNumero.format(tiendaService.calcularGananciasMensuales()),
                "anuales", formatoNumero.format(tiendaService.calcularGananciasAnuales())
            ),
            "fecha_consulta", LocalDateTime.now().format(formatoFechaHora),
            "moneda", "CLP"
        );
    }
}
