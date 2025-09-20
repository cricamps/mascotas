package mascotas.mascotas.controller;

import mascotas.mascotas.model.Venta;
import mascotas.mascotas.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    // GET /ventas - Obtener todas las ventas
    @GetMapping
    public ResponseEntity<Map<String, Object>> obtenerTodasLasVentas() {
        List<Venta> ventas = ventaService.obtenerTodasLasVentas();
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Lista de ventas obtenida exitosamente");
        respuesta.put("total", ventas.size());
        respuesta.put("datos", ventas);
        
        return ResponseEntity.ok(respuesta);
    }

    // GET /ventas/{id} - Obtener venta por ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerVentaPorId(@PathVariable Long id) {
        Optional<Venta> venta = ventaService.buscarVentaPorId(id);
        
        Map<String, Object> respuesta = new HashMap<>();
        if (venta.isPresent()) {
            respuesta.put("mensaje", "Venta encontrada");
            respuesta.put("datos", venta.get());
            return ResponseEntity.ok(respuesta);
        } else {
            respuesta.put("mensaje", "Venta no encontrada");
            respuesta.put("id", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    // GET /ventas/producto/{productoId} - Obtener ventas por producto
    @GetMapping("/producto/{productoId}")
    public ResponseEntity<Map<String, Object>> obtenerVentasPorProducto(@PathVariable Long productoId) {
        List<Venta> ventas = ventaService.obtenerVentasPorProducto(productoId);
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Ventas del producto ID: " + productoId);
        respuesta.put("total", ventas.size());
        respuesta.put("datos", ventas);
        
        return ResponseEntity.ok(respuesta);
    }

    // GET /ventas/hoy - Obtener ventas del día
    @GetMapping("/hoy")
    public ResponseEntity<Map<String, Object>> obtenerVentasDelDia() {
        List<Venta> ventas = ventaService.obtenerVentasDelDia();
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Ventas del día actual");
        respuesta.put("total", ventas.size());
        respuesta.put("datos", ventas);
        
        return ResponseEntity.ok(respuesta);
    }

    // POST /ventas - Crear nueva venta
    @PostMapping
    public ResponseEntity<Map<String, Object>> crearVenta(@Valid @RequestBody Venta venta) {
        try {
            Venta nuevaVenta = ventaService.crearVenta(venta);
            
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Venta registrada exitosamente");
            respuesta.put("datos", nuevaVenta);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        } catch (RuntimeException e) {
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Error al crear venta");
            respuesta.put("error", e.getMessage());
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
    }

    // GET /ventas/mas-vendidos - Productos más vendidos
    @GetMapping("/mas-vendidos")
    public ResponseEntity<Map<String, Object>> obtenerProductosMasVendidos() {
        List<Object[]> productos = ventaService.obtenerProductosMasVendidos();
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Productos más vendidos");
        respuesta.put("total", productos.size());
        respuesta.put("datos", productos);
        
        return ResponseEntity.ok(respuesta);
    }
}
