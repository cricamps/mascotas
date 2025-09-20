package mascotas.mascotas.repository;

import mascotas.mascotas.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    
    // Buscar ventas por producto
    List<Venta> findByProductoId(Long productoId);
    
    // Buscar ventas por fecha
    List<Venta> findByFechaVentaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    // Calcular ganancias por período
    @Query("SELECT SUM(v.total) FROM Venta v WHERE v.fechaVenta BETWEEN :fechaInicio AND :fechaFin")
    BigDecimal calcularGananciasPorPeriodo(@Param("fechaInicio") LocalDateTime fechaInicio, 
                                          @Param("fechaFin") LocalDateTime fechaFin);
    
    // Ventas del día actual - SOLUCIONADO para LocalDateTime + Oracle
    @Query("SELECT v FROM Venta v WHERE v.fechaVenta >= :inicioDelDia AND v.fechaVenta < :finDelDia")
    List<Venta> findVentasDelDia(@Param("inicioDelDia") LocalDateTime inicioDelDia, @Param("finDelDia") LocalDateTime finDelDia);
    
    // Ganancias del día actual - SOLUCIONADO para LocalDateTime + Oracle
    @Query("SELECT COALESCE(SUM(v.total), 0) FROM Venta v WHERE v.fechaVenta >= :inicioDelDia AND v.fechaVenta < :finDelDia")
    BigDecimal calcularGananciasDelDia(@Param("inicioDelDia") LocalDateTime inicioDelDia, @Param("finDelDia") LocalDateTime finDelDia);
    
    // Ganancias del mes actual - COMPATIBLE con Oracle + LocalDateTime
    @Query("SELECT COALESCE(SUM(v.total), 0) FROM Venta v WHERE EXTRACT(MONTH FROM v.fechaVenta) = :mes AND EXTRACT(YEAR FROM v.fechaVenta) = :anio")
    BigDecimal calcularGananciasDelMes(@Param("mes") int mes, @Param("anio") int anio);
    
    // Ganancias del año actual - COMPATIBLE con Oracle + LocalDateTime
    @Query("SELECT COALESCE(SUM(v.total), 0) FROM Venta v WHERE EXTRACT(YEAR FROM v.fechaVenta) = :anio")
    BigDecimal calcularGananciasDelAno(@Param("anio") int anio);
    
    // Productos más vendidos
    @Query("SELECT v.nombreProducto, SUM(v.cantidad) as totalVendido FROM Venta v GROUP BY v.nombreProducto ORDER BY totalVendido DESC")
    List<Object[]> findProductosMasVendidos();
}
