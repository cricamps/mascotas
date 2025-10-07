package mascotas.mascotas.controller;

import mascotas.mascotas.model.Venta;
import mascotas.mascotas.service.VentaService;
import mascotas.mascotas.exception.VentaNotFoundException;
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
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping
    public ResponseEntity<CollectionModel<Venta>> obtenerTodasLasVentas() {
        List<Venta> ventas = ventaService.obtenerTodasLasVentas();
        
        for (Venta venta : ventas) {
            agregarEnlacesVenta(venta);
        }
        
        Link selfLink = linkTo(methodOn(VentaController.class).obtenerTodasLasVentas()).withSelfRel();
        CollectionModel<Venta> collectionModel = CollectionModel.of(ventas, selfLink);
        
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venta> obtenerVentaPorId(@PathVariable Long id) {
        Optional<Venta> venta = ventaService.buscarVentaPorId(id);
        
        if (venta.isPresent()) {
            Venta ventaEncontrada = venta.get();
            agregarEnlacesVenta(ventaEncontrada);
            return ResponseEntity.ok(ventaEncontrada);
        } else {
            throw new VentaNotFoundException(id);
        }
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<CollectionModel<Venta>> obtenerVentasPorProducto(@PathVariable Long productoId) {
        List<Venta> ventas = ventaService.obtenerVentasPorProducto(productoId);
        
        for (Venta venta : ventas) {
            agregarEnlacesVenta(venta);
        }
        
        Link selfLink = linkTo(methodOn(VentaController.class).obtenerVentasPorProducto(productoId)).withSelfRel();
        CollectionModel<Venta> collectionModel = CollectionModel.of(ventas, selfLink);
        
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/hoy")
    public ResponseEntity<CollectionModel<Venta>> obtenerVentasDelDia() {
        List<Venta> ventas = ventaService.obtenerVentasDelDia();
        
        for (Venta venta : ventas) {
            agregarEnlacesVenta(venta);
        }
        
        Link selfLink = linkTo(methodOn(VentaController.class).obtenerVentasDelDia()).withSelfRel();
        CollectionModel<Venta> collectionModel = CollectionModel.of(ventas, selfLink);
        
        return ResponseEntity.ok(collectionModel);
    }

    @PostMapping
    public ResponseEntity<Venta> crearVenta(@Valid @RequestBody Venta venta) {
        Venta nuevaVenta = ventaService.crearVenta(venta);
        agregarEnlacesVenta(nuevaVenta);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaVenta);
    }

    private void agregarEnlacesVenta(Venta venta) {
        Link selfLink = linkTo(methodOn(VentaController.class).obtenerVentaPorId(venta.getId())).withSelfRel();
        venta.add(selfLink);
        
        Link allVentasLink = linkTo(methodOn(VentaController.class).obtenerTodasLasVentas()).withRel("todas-ventas");
        venta.add(allVentasLink);
        
        if (venta.getProductoId() != null) {
            Link productoLink = linkTo(ProductoController.class).slash(venta.getProductoId()).withRel("producto");
            venta.add(productoLink);
        }
    }

    @ExceptionHandler(VentaNotFoundException.class)
    public ResponseEntity<String> handleVentaNotFound(VentaNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
