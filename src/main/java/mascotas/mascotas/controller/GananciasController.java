package mascotas.mascotas.controller;

import mascotas.mascotas.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/ganancias")
public class GananciasController {

    @Autowired
    private VentaService ventaService;
    
    private final NumberFormat formatoNumero = DecimalFormat.getInstance(new Locale("es", "CL"));
    private final DateTimeFormatter formatoFechaHora = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

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
    public ResponseEntity<Map<String, Object>> obtenerGananciasDiarias() {
        BigDecimal ganancias = ventaService.calcularGananciasDiarias();
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Ganancias del día actual");
        respuesta.put("periodo", "Diarias");
        respuesta.put("fecha", java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        respuesta.put("ganancia_valor", ganancias);
        respuesta.put("ganancia_formateada", "$" + formatoNumero.format(ganancias));
        respuesta.put("moneda", "CLP");
        
        return ResponseEntity.ok(respuesta);
    }

    // GET /ganancias/mensuales - Ganancias del mes actual
    @GetMapping("/mensuales")
    public ResponseEntity<Map<String, Object>> obtenerGananciasMensuales() {
        BigDecimal ganancias = ventaService.calcularGananciasMensuales();
        java.time.LocalDate fechaActual = java.time.LocalDate.now();
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Ganancias del mes actual");
        respuesta.put("periodo", "Mensuales");
        respuesta.put("mes", obtenerNombreMes(fechaActual.getMonthValue()));
        respuesta.put("numero_mes", String.format("%02d", fechaActual.getMonthValue()));
        respuesta.put("año", fechaActual.getYear());
        respuesta.put("ganancia_valor", ganancias);
        respuesta.put("ganancia_formateada", "$" + formatoNumero.format(ganancias));
        respuesta.put("moneda", "CLP");
        
        return ResponseEntity.ok(respuesta);
    }

    // GET /ganancias/anuales - Ganancias del año actual
    @GetMapping("/anuales")
    public ResponseEntity<Map<String, Object>> obtenerGananciasAnuales() {
        BigDecimal ganancias = ventaService.calcularGananciasAnuales();
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Ganancias del año actual");
        respuesta.put("periodo", "Anuales");
        respuesta.put("año", java.time.LocalDate.now().getYear());
        respuesta.put("ganancia_valor", ganancias);
        respuesta.put("ganancia_formateada", "$" + formatoNumero.format(ganancias));
        respuesta.put("moneda", "CLP");
        
        return ResponseEntity.ok(respuesta);
    }

    // GET /ganancias - Resumen de todas las ganancias
    @GetMapping
    public ResponseEntity<Map<String, Object>> obtenerResumenGanancias() {
        BigDecimal gananciasDiarias = ventaService.calcularGananciasDiarias();
        BigDecimal gananciasMensuales = ventaService.calcularGananciasMensuales();
        BigDecimal gananciasAnuales = ventaService.calcularGananciasAnuales();
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Resumen completo de ganancias");
        
        Map<String, Object> ganancias = new HashMap<>();
        ganancias.put("diarias", Map.of(
            "valor", gananciasDiarias,
            "formateada", "$" + formatoNumero.format(gananciasDiarias)
        ));
        ganancias.put("mensuales", Map.of(
            "valor", gananciasMensuales,
            "formateada", "$" + formatoNumero.format(gananciasMensuales)
        ));
        ganancias.put("anuales", Map.of(
            "valor", gananciasAnuales,
            "formateada", "$" + formatoNumero.format(gananciasAnuales)
        ));
        
        respuesta.put("ganancias", ganancias);
        respuesta.put("fecha_consulta", LocalDateTime.now().format(formatoFechaHora));
        respuesta.put("moneda", "CLP");
        
        return ResponseEntity.ok(respuesta);
    }
}
