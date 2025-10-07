package mascotas.mascotas.service;

import mascotas.mascotas.model.Venta;
import mascotas.mascotas.model.Producto;
import mascotas.mascotas.repository.VentaRepository;
import mascotas.mascotas.repository.ProductoRepository;
import mascotas.mascotas.exception.ProductoNotFoundException;
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
    
    public List<Venta> obtenerTodasLasVentas() {
        return ventaRepository.findAll();
    }
    
    public Optional<Venta> buscarVentaPorId(Long id) {
        return ventaRepository.findById(id);
    }
    
    public Venta crearVenta(@Valid Venta venta) {
        Optional<Producto> producto = productoRepository.findById(venta.getProductoId());
        if (producto.isPresent()) {
            Producto prod = producto.get();
            
            if (prod.getStock() >= venta.getCantidad()) {
                prod.setStock(prod.getStock() - venta.getCantidad());
                productoRepository.save(prod);
                
                return ventaRepository.save(venta);
            } else {
                throw new RuntimeException("Stock insuficiente. Stock disponible: " + prod.getStock());
            }
        } else {
            throw new ProductoNotFoundException(venta.getProductoId());
        }
    }
    
    public List<Venta> obtenerVentasPorProducto(Long productoId) {
        return ventaRepository.findByProductoId(productoId);
    }
    
    public List<Venta> obtenerVentasDelDia() {
        LocalDate hoy = LocalDate.now();
        LocalDateTime inicioDelDia = hoy.atStartOfDay();
        LocalDateTime finDelDia = hoy.plusDays(1).atStartOfDay();
        return ventaRepository.findVentasDelDia(inicioDelDia, finDelDia);
    }
    
    public BigDecimal calcularGananciasDiarias() {
        LocalDate hoy = LocalDate.now();
        LocalDateTime inicioDelDia = hoy.atStartOfDay();
        LocalDateTime finDelDia = hoy.plusDays(1).atStartOfDay();
        BigDecimal ganancias = ventaRepository.calcularGananciasDelDia(inicioDelDia, finDelDia);
        return ganancias != null ? ganancias : BigDecimal.ZERO;
    }
    
    public BigDecimal calcularGananciasMensuales() {
        LocalDate hoy = LocalDate.now();
        int mesActual = hoy.getMonthValue();
        int anioActual = hoy.getYear();
        BigDecimal ganancias = ventaRepository.calcularGananciasDelMes(mesActual, anioActual);
        return ganancias != null ? ganancias : BigDecimal.ZERO;
    }
    
    public BigDecimal calcularGananciasAnuales() {
        LocalDate hoy = LocalDate.now();
        int anioActual = hoy.getYear();
        BigDecimal ganancias = ventaRepository.calcularGananciasDelAno(anioActual);
        return ganancias != null ? ganancias : BigDecimal.ZERO;
    }
    
    public List<Venta> obtenerVentasPorPeriodo(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return ventaRepository.findByFechaVentaBetween(fechaInicio, fechaFin);
    }
    
    public List<Object[]> obtenerProductosMasVendidos() {
        return ventaRepository.findProductosMasVendidos();
    }
}
