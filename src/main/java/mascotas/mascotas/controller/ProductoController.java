package mascotas.mascotas.controller;

import mascotas.mascotas.model.Producto;
import mascotas.mascotas.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // GET /productos - Obtener todos los productos
    @GetMapping
    public ResponseEntity<Map<String, Object>> obtenerTodosLosProductos() {
        List<Producto> productos = productoService.obtenerTodosLosProductos();
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Lista de productos obtenida exitosamente");
        respuesta.put("total", productos.size());
        respuesta.put("datos", productos);
        
        return ResponseEntity.ok(respuesta);
    }

    // GET /productos/{id} - Obtener producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerProductoPorId(@PathVariable Long id) {
        Optional<Producto> producto = productoService.buscarProductoPorId(id);
        
        Map<String, Object> respuesta = new HashMap<>();
        if (producto.isPresent()) {
            respuesta.put("mensaje", "Producto encontrado");
            respuesta.put("datos", producto.get());
            return ResponseEntity.ok(respuesta);
        } else {
            respuesta.put("mensaje", "Producto no encontrado");
            respuesta.put("id", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    // GET /productos/activos - Obtener productos activos
    @GetMapping("/activos")
    public ResponseEntity<Map<String, Object>> obtenerProductosActivos() {
        List<Producto> productosActivos = productoService.obtenerProductosActivos();
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Lista de productos activos");
        respuesta.put("total", productosActivos.size());
        respuesta.put("datos", productosActivos);
        
        return ResponseEntity.ok(respuesta);
    }

    // GET /productos/categoria/{categoria} - Obtener productos por categoría
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<Map<String, Object>> obtenerProductosPorCategoria(@PathVariable String categoria) {
        List<Producto> productos = productoService.obtenerProductosPorCategoria(categoria);
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Productos de categoría: " + categoria);
        respuesta.put("total", productos.size());
        respuesta.put("datos", productos);
        
        return ResponseEntity.ok(respuesta);
    }

    // GET /productos/tipo/{tipoMascota} - Obtener productos por tipo de mascota
    @GetMapping("/tipo/{tipoMascota}")
    public ResponseEntity<Map<String, Object>> obtenerProductosPorTipoMascota(@PathVariable String tipoMascota) {
        List<Producto> productos = productoService.obtenerProductosPorTipoMascota(tipoMascota);
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Productos para: " + tipoMascota);
        respuesta.put("total", productos.size());
        respuesta.put("datos", productos);
        
        return ResponseEntity.ok(respuesta);
    }

    // GET /productos/marca/{marca} - Obtener productos por marca
    @GetMapping("/marca/{marca}")
    public ResponseEntity<Map<String, Object>> obtenerProductosPorMarca(@PathVariable String marca) {
        List<Producto> productos = productoService.obtenerProductosPorMarca(marca);
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Productos de marca: " + marca);
        respuesta.put("total", productos.size());
        respuesta.put("datos", productos);
        
        return ResponseEntity.ok(respuesta);
    }

    // GET /productos/disponibles - Obtener productos con stock
    @GetMapping("/disponibles")
    public ResponseEntity<Map<String, Object>> obtenerProductosConStock() {
        List<Producto> productosDisponibles = productoService.obtenerProductosConStock();
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Productos disponibles en stock");
        respuesta.put("total", productosDisponibles.size());
        respuesta.put("datos", productosDisponibles);
        
        return ResponseEntity.ok(respuesta);
    }

    // GET /productos/precio - Obtener productos por rango de precio
    @GetMapping("/precio")
    public ResponseEntity<Map<String, Object>> obtenerProductosPorRangoPrecios(
            @RequestParam BigDecimal min, 
            @RequestParam BigDecimal max) {
        List<Producto> productos = productoService.obtenerProductosPorRangoPrecios(min, max);
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Productos entre $" + min + " y $" + max);
        respuesta.put("total", productos.size());
        respuesta.put("datos", productos);
        
        return ResponseEntity.ok(respuesta);
    }

    // GET /productos/buscar/{texto} - Buscar productos por nombre
    @GetMapping("/buscar/{texto}")
    public ResponseEntity<Map<String, Object>> buscarProductosPorNombre(@PathVariable String texto) {
        List<Producto> productos = productoService.buscarProductosPorNombre(texto);
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Productos que contienen: " + texto);
        respuesta.put("total", productos.size());
        respuesta.put("datos", productos);
        
        return ResponseEntity.ok(respuesta);
    }

    // POST /productos - Crear nuevo producto
    @PostMapping
    public ResponseEntity<Map<String, Object>> crearProducto(@Valid @RequestBody Producto producto) {
        Producto nuevoProducto = productoService.crearProducto(producto);
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Producto creado exitosamente");
        respuesta.put("datos", nuevoProducto);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    // PUT /productos/{id} - Actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizarProducto(@PathVariable Long id, @Valid @RequestBody Producto producto) {
        try {
            Producto productoActualizado = productoService.actualizarProducto(id, producto);
            
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Producto actualizado exitosamente");
            respuesta.put("datos", productoActualizado);
            
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Error al actualizar producto");
            respuesta.put("error", e.getMessage());
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    // DELETE /productos/{id} - Eliminar producto (inteligente: físico si no tiene historial, lógico si tiene historial)
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminarProducto(@PathVariable Long id) {
        try {
            String tipoEliminacion = productoService.eliminarProducto(id);
            
            Map<String, Object> respuesta = new HashMap<>();
            if ("FISICA".equals(tipoEliminacion)) {
                respuesta.put("mensaje", "Producto eliminado físicamente - Sin historial de ventas");
                respuesta.put("tipo", "FISICA");
                respuesta.put("razon", "Producto sin ventas asociadas");
            } else {
                respuesta.put("mensaje", "Eliminación lógica aplicada - Producto tiene historial");
                respuesta.put("tipo", "LOGICA");
                respuesta.put("razon", "Producto con ventas asociadas - Se preserva historial");
            }
            respuesta.put("id", id);
            
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Error al eliminar producto");
            respuesta.put("error", e.getMessage());
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    // PUT /productos/{id}/activar - Activar producto
    @PutMapping("/{id}/activar")
    public ResponseEntity<Map<String, Object>> activarProducto(@PathVariable Long id) {
        try {
            Producto producto = productoService.activarProducto(id);
            
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Producto activado exitosamente");
            respuesta.put("datos", producto);
            
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Error al activar producto");
            respuesta.put("error", e.getMessage());
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }
}
