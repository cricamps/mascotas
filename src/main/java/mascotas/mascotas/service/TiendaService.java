package mascotas.mascotas.service;

import mascotas.mascotas.model.Producto;
import mascotas.mascotas.model.Venta;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TiendaService {
    
    private List<Producto> productos;
    private List<Venta> ventas;

    public TiendaService() {
        inicializarProductos();
        inicializarVentas();
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

    private void inicializarVentas() {
        ventas = new ArrayList<>();
        
        // Ventas de hoy
        LocalDateTime hoy = LocalDateTime.now();
        ventas.add(new Venta(1L, 1L, "Alimento Premium Perro", 2, 15990.0, hoy));
        ventas.add(new Venta(2L, 3L, "Pelota de Goma", 1, 3500.0, hoy));
        
        // Ventas de ayer
        LocalDateTime ayer = hoy.minusDays(1);
        ventas.add(new Venta(3L, 2L, "Alimento Gato Castrado", 1, 12990.0, ayer));
        
        // Ventas del mes pasado
        LocalDateTime mesPasado = hoy.minusMonths(1);
        ventas.add(new Venta(4L, 4L, "Rascador para Gatos", 1, 25990.0, mesPasado));
        ventas.add(new Venta(5L, 5L, "Collar Antipulgas", 3, 8990.0, mesPasado));
        ventas.add(new Venta(6L, 6L, "Champú Medicinal", 2, 6990.0, mesPasado));
        
        // Ventas del año pasado
        LocalDateTime añoPasado = hoy.minusYears(1);
        ventas.add(new Venta(7L, 7L, "Comida para Peces", 4, 4990.0, añoPasado));
        ventas.add(new Venta(8L, 8L, "Arena Sanitaria", 2, 7990.0, añoPasado));
    }

    // Métodos para productos
    public List<Producto> obtenerTodosLosProductos() {
        return new ArrayList<>(productos);
    }

    public Producto buscarProductoPorId(Long id) {
        return productos.stream()
                .filter(producto -> producto.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Producto> buscarProductosPorCategoria(String categoria) {
        return productos.stream()
                .filter(producto -> producto.getCategoria().equalsIgnoreCase(categoria))
                .collect(Collectors.toList());
    }

    public List<Producto> obtenerProductosDisponibles() {
        return productos.stream()
                .filter(Producto::tieneStock)
                .collect(Collectors.toList());
    }

    // Métodos para ventas y ganancias
    public List<Venta> obtenerTodasLasVentas() {
        return new ArrayList<>(ventas);
    }

    public Double calcularGananciasDiarias() {
        LocalDate hoy = LocalDate.now();
        return ventas.stream()
                .filter(venta -> venta.getFechaVenta().toLocalDate().equals(hoy))
                .mapToDouble(Venta::getTotal)
                .sum();
    }

    public Double calcularGananciasMensuales() {
        LocalDate inicioMes = LocalDate.now().withDayOfMonth(1);
        LocalDate finMes = inicioMes.plusMonths(1).minusDays(1);
        
        return ventas.stream()
                .filter(venta -> {
                    LocalDate fechaVenta = venta.getFechaVenta().toLocalDate();
                    return !fechaVenta.isBefore(inicioMes) && !fechaVenta.isAfter(finMes);
                })
                .mapToDouble(Venta::getTotal)
                .sum();
    }

    public Double calcularGananciasAnuales() {
        int añoActual = LocalDate.now().getYear();
        return ventas.stream()
                .filter(venta -> venta.getFechaVenta().getYear() == añoActual)
                .mapToDouble(Venta::getTotal)
                .sum();
    }
}
