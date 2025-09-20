package mascotas.mascotas.service;

import mascotas.mascotas.model.Producto;
import mascotas.mascotas.repository.ProductoRepository;
import mascotas.mascotas.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private VentaRepository ventaRepository;
    
    // Obtener todos los productos
    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }
    
    // Buscar producto por ID
    public Optional<Producto> buscarProductoPorId(Long id) {
        return productoRepository.findById(id);
    }
    
    // Obtener productos activos
    public List<Producto> obtenerProductosActivos() {
        return productoRepository.findByActivoTrue();
    }
    
    // Obtener productos por categoría
    public List<Producto> obtenerProductosPorCategoria(String categoria) {
        return productoRepository.findByCategoria(categoria);
    }
    
    // Obtener productos por tipo de mascota
    public List<Producto> obtenerProductosPorTipoMascota(String tipoMascota) {
        return productoRepository.findByTipoMascota(tipoMascota);
    }
    
    // Obtener productos por marca
    public List<Producto> obtenerProductosPorMarca(String marca) {
        return productoRepository.findByMarca(marca);
    }
    
    // Obtener productos con stock
    public List<Producto> obtenerProductosConStock() {
        return productoRepository.findProductosConStock();
    }
    
    // Buscar productos por rango de precios
    public List<Producto> obtenerProductosPorRangoPrecios(BigDecimal precioMin, BigDecimal precioMax) {
        return productoRepository.findByRangoPrecios(precioMin, precioMax);
    }
    
    // Buscar productos por nombre
    public List<Producto> buscarProductosPorNombre(String texto) {
        return productoRepository.findByNombreContaining(texto);
    }
    
    // Buscar productos por categoría y tipo de mascota
    public List<Producto> obtenerProductosPorCategoriaYTipo(String categoria, String tipoMascota) {
        return productoRepository.findByCategoriaAndTipoMascotaAndActivoTrue(categoria, tipoMascota);
    }
    
    // Crear nuevo producto
    public Producto crearProducto(@Valid Producto producto) {
        return productoRepository.save(producto);
    }
    
    // Actualizar producto
    public Producto actualizarProducto(Long id, @Valid Producto productoActualizado) {
        Optional<Producto> productoExistente = productoRepository.findById(id);
        if (productoExistente.isPresent()) {
            Producto producto = productoExistente.get();
            producto.setNombre(productoActualizado.getNombre());
            producto.setCategoria(productoActualizado.getCategoria());
            producto.setPrecio(productoActualizado.getPrecio());
            producto.setStock(productoActualizado.getStock());
            producto.setDescripcion(productoActualizado.getDescripcion());
            producto.setMarca(productoActualizado.getMarca());
            producto.setTipoMascota(productoActualizado.getTipoMascota());
            producto.setActivo(productoActualizado.getActivo());
            return productoRepository.save(producto);
        }
        throw new RuntimeException("Producto no encontrado con ID: " + id);
    }
    
    // Eliminar producto (eliminación inteligente: física si no tiene historial, lógica si tiene historial)
    public String eliminarProducto(Long id) {
        // Verificar que el producto existe
        Optional<Producto> producto = productoRepository.findById(id);
        if (!producto.isPresent()) {
            throw new RuntimeException("Producto no encontrado con ID: " + id);
        }
        
        // Verificar si tiene historial de ventas
        if (ventaRepository.findByProductoId(id).isEmpty()) {
            // No tiene ventas, se puede eliminar físicamente
            productoRepository.deleteById(id);
            return "FISICA"; // Eliminación física exitosa
        } else {
            // Tiene ventas, solo eliminación lógica
            Producto prod = producto.get();
            prod.setActivo(false);
            productoRepository.save(prod);
            return "LOGICA"; // Se realizó eliminación lógica por seguridad
        }
    }
    
    // Activar producto
    public Producto activarProducto(Long id) {
        Optional<Producto> producto = productoRepository.findById(id);
        if (producto.isPresent()) {
            Producto prod = producto.get();
            prod.setActivo(true);
            return productoRepository.save(prod);
        }
        throw new RuntimeException("Producto no encontrado con ID: " + id);
    }
}
