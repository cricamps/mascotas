package mascotas.mascotas.service;

import mascotas.mascotas.model.Venta;
import mascotas.mascotas.model.Producto;
import mascotas.mascotas.repository.VentaRepository;
import mascotas.mascotas.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VentaService {
    
    @Autowired
    private VentaRepository ventaRepository;
    
    @Autowired
    private ProductoRepository productoRepository;
    
    // Obtener todas las ventas
    public List<Venta> obtenerTodasLasVentas() {
        return ventaRepository.findAll();
    }
    
    // Buscar venta por ID
    public Optional<Venta> buscarVentaPorId(Long id) {
        return ventaRepository.findById(id);
    }
    
    // Crear nueva venta
    public Venta crearVenta(@Valid Venta venta) {
        // Verificar que el producto existe
        Optional<Producto> producto = productoRepository.findById(venta.getProductoId());
        if (producto.isPresent()) {
            Producto prod = producto.get();
            
            // Verificar stock suficiente
            if (prod.getStock() >= venta.getCantidad()) {
                // Actualizar stock del producto
                prod.setStock(prod.getStock() - venta.getCantidad());
                productoRepository.save(prod);
                
                // Crear la venta
                return ventaRepository.save(venta);
            } else {
                throw new RuntimeException("Stock insuficiente. Stock disponible: " + prod.getStock());
            }
        } else {
            throw new RuntimeException("Producto no encontrado con ID: " + venta.getProductoId());
        }
    }
    
    // Obtener ventas por producto
    public List<Venta> obtenerVentasPorProducto(Long productoId) {
        return ventaRepository.findByProductoId(productoId);
    }
    
    // Obtener ventas del día - ACTUALIZADO para usar parámetros
    public List<Venta> obtenerVentasDelDia() {
        LocalDate hoy = LocalDate.now();
        LocalDateTime inicioDelDia = hoy.atStartOfDay();
        LocalDateTime finDelDia = hoy.plusDays(1).atStartOfDay();
        return ventaRepository.findVentasDelDia(inicioDelDia, finDelDia);
    }
    
    // Calcular ganancias del día - ACTUALIZADO para usar parámetros
    public BigDecimal calcularGananciasDiarias() {
        LocalDate hoy = LocalDate.now();
        LocalDateTime inicioDelDia = hoy.atStartOfDay();
        LocalDateTime finDelDia = hoy.plusDays(1).atStartOfDay();
        BigDecimal ganancias = ventaRepository.calcularGananciasDelDia(inicioDelDia, finDelDia);
        return ganancias != null ? ganancias : BigDecimal.ZERO;
    }
    
    // Calcular ganancias del mes - ACTUALIZADO para usar parámetros
    public BigDecimal calcularGananciasMensuales() {
        LocalDate hoy = LocalDate.now();
        int mesActual = hoy.getMonthValue();
        int anioActual = hoy.getYear();
        BigDecimal ganancias = ventaRepository.calcularGananciasDelMes(mesActual, anioActual);
        return ganancias != null ? ganancias : BigDecimal.ZERO;
    }
    
    // Calcular ganancias del año - ACTUALIZADO para usar parámetros
    public BigDecimal calcularGananciasAnuales() {
        LocalDate hoy = LocalDate.now();
        int anioActual = hoy.getYear();
        BigDecimal ganancias = ventaRepository.calcularGananciasDelAno(anioActual);
        return ganancias != null ? ganancias : BigDecimal.ZERO;
    }
    
    // Obtener ventas por período
    public List<Venta> obtenerVentasPorPeriodo(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return ventaRepository.findByFechaVentaBetween(fechaInicio, fechaFin);
    }
    
    // Obtener productos más vendidos
    public List<Object[]> obtenerProductosMasVendidos() {
        return ventaRepository.findProductosMasVendidos();
    }
}
