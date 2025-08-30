package mascotas.mascotas.controller;

import mascotas.mascotas.model.Producto;
import mascotas.mascotas.service.TiendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private TiendaService tiendaService;

    // GET /productos - mostrar todos los productos
    @GetMapping
    public List<Producto> obtenerProductos() {
        return tiendaService.obtenerTodosLosProductos();
    }

    // GET /productos/{id} - mostrar producto por ID
    @GetMapping("/{id}")
    public Producto obtenerProductoPorId(@PathVariable Long id) {
        return tiendaService.buscarProductoPorId(id);
    }

    // GET /productos/categoria/{categoria} - mostrar por categoría
    @GetMapping("/categoria/{categoria}")
    public List<Producto> obtenerProductosPorCategoria(@PathVariable String categoria) {
        return tiendaService.buscarProductosPorCategoria(categoria);
    }

    // GET /productos/disponibles - mostrar productos con stock
    @GetMapping("/disponibles")
    public List<Producto> obtenerProductosDisponibles() {
        return tiendaService.obtenerProductosDisponibles();
    }
}
