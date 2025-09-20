package mascotas.mascotas.repository;

import mascotas.mascotas.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    // Buscar productos activos
    List<Producto> findByActivoTrue();
    
    // Buscar por categoría
    List<Producto> findByCategoria(String categoria);
    
    // Buscar por tipo de mascota
    List<Producto> findByTipoMascota(String tipoMascota);
    
    // Buscar por marca
    List<Producto> findByMarca(String marca);
    
    // Buscar productos con stock disponible
    @Query("SELECT p FROM Producto p WHERE p.stock > 0 AND p.activo = true")
    List<Producto> findProductosConStock();
    
    // Buscar productos por rango de precio
    @Query("SELECT p FROM Producto p WHERE p.precio BETWEEN :precioMin AND :precioMax AND p.activo = true")
    List<Producto> findByRangoPrecios(@Param("precioMin") BigDecimal precioMin, 
                                     @Param("precioMax") BigDecimal precioMax);
    
    // Buscar productos por nombre que contenga texto
    @Query("SELECT p FROM Producto p WHERE UPPER(p.nombre) LIKE UPPER(CONCAT('%', :texto, '%')) AND p.activo = true")
    List<Producto> findByNombreContaining(@Param("texto") String texto);
    
    // Buscar productos por categoría y tipo de mascota
    List<Producto> findByCategoriaAndTipoMascotaAndActivoTrue(String categoria, String tipoMascota);
}
