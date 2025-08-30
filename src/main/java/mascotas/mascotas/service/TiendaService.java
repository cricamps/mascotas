package mascotas.mascotas.service;

import mascotas.mascotas.model.Producto;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TiendaService {
    
    private List<Producto> productos;

    public TiendaService() {
        inicializarProductos();
    }

    private void inicializarProductos() {
        productos = new ArrayList<>();
        
        // Productos
        productos.add(new Producto(1L, "Alimento Premium Perro", "ALIMENTO", 15990.0, 25, 
                "Alimento balanceado para perros adultos", "Royal Canin", "PERRO"));
        productos.add(new Producto(2L, "Alimento Gato Castrado", "ALIMENTO", 12990.0, 30, 
                "Alimento especializado para gatos castrados", "Hill's", "GATO"));
        productos.add(new Producto(3L, "Pelota de Goma", "JUGUETE", 3500.0, 15, 
                "Pelota resistente para jugar", "Kong", "PERRO"));
        productos.add(new Producto(4L, "Rascador para Gatos", "ACCESORIO", 25990.0, 10, 
                "Torre rascador con múltiples niveles", "Catit", "GATO"));
        productos.add(new Producto(5L, "Collar Antipulgas", "MEDICINA", 8990.0, 20, 
                "Collar con protección de 8 meses", "Bayer", "PERRO"));
        productos.add(new Producto(6L, "Champú Medicinal", "CUIDADO", 6990.0, 18, 
                "Champú para problemas de piel", "Virbac", "PERRO"));
        productos.add(new Producto(7L, "Comida para Peces", "ALIMENTO", 4990.0, 35, 
                "Escamas nutritivas para peces tropicales", "Tetra", "PEZ"));
        productos.add(new Producto(8L, "Arena Sanitaria", "CUIDADO", 7990.0, 40, 
                "Arena aglomerante sin polvo", "Gatos Best", "GATO"));
    }

    // Método para obtener todos los productos
    public List<Producto> obtenerTodosLosProductos() {
        return new ArrayList<>(productos);
    }

    // Método para buscar producto por ID
    public Producto buscarProductoPorId(Long id) {
        return productos.stream()
                .filter(producto -> producto.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Método para buscar productos por categoría
    public List<Producto> buscarProductosPorCategoria(String categoria) {
        return productos.stream()
                .filter(producto -> producto.getCategoria().equalsIgnoreCase(categoria))
                .collect(Collectors.toList());
    }

    // Método para obtener productos disponibles (con stock)
    public List<Producto> obtenerProductosDisponibles() {
        return productos.stream()
                .filter(Producto::tieneStock)
                .collect(Collectors.toList());
    }
}
