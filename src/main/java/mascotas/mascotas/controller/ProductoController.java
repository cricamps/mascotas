package mascotas.mascotas.controller;

import mascotas.mascotas.model.Producto;
import mascotas.mascotas.service.ProductoService;
import mascotas.mascotas.exception.ProductoNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<CollectionModel<Producto>> obtenerTodosLosProductos() {
        List<Producto> productos = productoService.obtenerTodosLosProductos();
        
        for (Producto producto : productos) {
            agregarEnlacesProducto(producto);
        }
        
        Link selfLink = linkTo(methodOn(ProductoController.class).obtenerTodosLosProductos()).withSelfRel();
        CollectionModel<Producto> collectionModel = CollectionModel.of(productos, selfLink);
        
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
        Optional<Producto> producto = productoService.buscarProductoPorId(id);
        
        if (producto.isPresent()) {
            Producto productoEncontrado = producto.get();
            agregarEnlacesProducto(productoEncontrado);
            return ResponseEntity.ok(productoEncontrado);
        } else {
            throw new ProductoNotFoundException(id);
        }
    }

    @GetMapping("/activos")
    public ResponseEntity<CollectionModel<Producto>> obtenerProductosActivos() {
        List<Producto> productosActivos = productoService.obtenerProductosActivos();
        
        for (Producto producto : productosActivos) {
            agregarEnlacesProducto(producto);
        }
        
        Link selfLink = linkTo(methodOn(ProductoController.class).obtenerProductosActivos()).withSelfRel();
        Link allLink = linkTo(methodOn(ProductoController.class).obtenerTodosLosProductos()).withRel("todos-productos");
        
        CollectionModel<Producto> collectionModel = CollectionModel.of(productosActivos, selfLink, allLink);
        
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<CollectionModel<Producto>> obtenerProductosPorCategoria(@PathVariable String categoria) {
        List<Producto> productos = productoService.obtenerProductosPorCategoria(categoria);
        
        for (Producto producto : productos) {
            agregarEnlacesProducto(producto);
        }
        
        Link selfLink = linkTo(methodOn(ProductoController.class).obtenerProductosPorCategoria(categoria)).withSelfRel();
        CollectionModel<Producto> collectionModel = CollectionModel.of(productos, selfLink);
        
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/tipo/{tipoMascota}")
    public ResponseEntity<CollectionModel<Producto>> obtenerProductosPorTipoMascota(@PathVariable String tipoMascota) {
        List<Producto> productos = productoService.obtenerProductosPorTipoMascota(tipoMascota);
        
        for (Producto producto : productos) {
            agregarEnlacesProducto(producto);
        }
        
        Link selfLink = linkTo(methodOn(ProductoController.class).obtenerProductosPorTipoMascota(tipoMascota)).withSelfRel();
        CollectionModel<Producto> collectionModel = CollectionModel.of(productos, selfLink);
        
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/disponibles")
    public ResponseEntity<CollectionModel<Producto>> obtenerProductosConStock() {
        List<Producto> productosDisponibles = productoService.obtenerProductosConStock();
        
        for (Producto producto : productosDisponibles) {
            agregarEnlacesProducto(producto);
        }
        
        Link selfLink = linkTo(methodOn(ProductoController.class).obtenerProductosConStock()).withSelfRel();
        CollectionModel<Producto> collectionModel = CollectionModel.of(productosDisponibles, selfLink);
        
        return ResponseEntity.ok(collectionModel);
    }

    @PostMapping
    public ResponseEntity<Producto> crearProducto(@Valid @RequestBody Producto producto) {
        Producto nuevoProducto = productoService.crearProducto(producto);
        agregarEnlacesProducto(nuevoProducto);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @Valid @RequestBody Producto producto) {
        Producto productoActualizado = productoService.actualizarProducto(id, producto);
        agregarEnlacesProducto(productoActualizado);
        
        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

    private void agregarEnlacesProducto(Producto producto) {
        Link selfLink = linkTo(methodOn(ProductoController.class).obtenerProductoPorId(producto.getId())).withSelfRel();
        producto.add(selfLink);
        
        Link allProductosLink = linkTo(methodOn(ProductoController.class).obtenerTodosLosProductos()).withRel("todos-productos");
        producto.add(allProductosLink);
        
        Link updateLink = linkTo(methodOn(ProductoController.class).actualizarProducto(producto.getId(), producto)).withRel("actualizar");
        producto.add(updateLink);
        
        Link deleteLink = linkTo(methodOn(ProductoController.class).eliminarProducto(producto.getId())).withRel("eliminar");
        producto.add(deleteLink);
        
        if (producto.getCategoria() != null) {
            Link categoriaLink = linkTo(methodOn(ProductoController.class).obtenerProductosPorCategoria(producto.getCategoria())).withRel("misma-categoria");
            producto.add(categoriaLink);
        }
    }

    @ExceptionHandler(ProductoNotFoundException.class)
    public ResponseEntity<String> handleProductoNotFound(ProductoNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
