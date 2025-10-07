package mascotas.mascotas.service;

import mascotas.mascotas.model.Producto;
import mascotas.mascotas.repository.ProductoRepository;
import mascotas.mascotas.repository.VentaRepository;
import mascotas.mascotas.exception.ProductoNotFoundException;
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
    
    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }
    
    public Optional<Producto> buscarProductoPorId(Long id) {
        return productoRepository.findById(id);
    }
    
    public List<Producto> obtenerProductosActivos() {
        return productoRepository.findByActivoTrue();
    }
    
    public List<Producto> obtenerProductosPorCategoria(String categoria) {
        return productoRepository.findByCategoria(categoria);
    }
    
    public List<Producto> obtenerProductosPorTipoMascota(String tipoMascota) {
        return productoRepository.findByTipoMascota(tipoMascota);
    }
    
    public List<Producto> obtenerProductosPorMarca(String marca) {
        return productoRepository.findByMarca(marca);
    }
    
    public List<Producto> obtenerProductosConStock() {
        return productoRepository.findProductosConStock();
    }
    
    public List<Producto> obtenerProductosPorRangoPrecios(BigDecimal precioMin, BigDecimal precioMax) {
        return productoRepository.findByRangoPrecios(precioMin, precioMax);
    }
    
    public List<Producto> buscarProductosPorNombre(String texto) {
        return productoRepository.findByNombreContaining(texto);
    }
    
    public List<Producto> obtenerProductosPorCategoriaYTipo(String categoria, String tipoMascota) {
        return productoRepository.findByCategoriaAndTipoMascotaAndActivoTrue(categoria, tipoMascota);
    }
    
    public Producto crearProducto(@Valid Producto producto) {
        return productoRepository.save(producto);
    }
    
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
        throw new ProductoNotFoundException(id);
    }
    
    public String eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ProductoNotFoundException(id);
        }
        
        if (ventaRepository.findByProductoId(id).isEmpty()) {
            productoRepository.deleteById(id);
            return "FISICA";
        } else {
            Producto producto = productoRepository.findById(id).get();
            producto.setActivo(false);
            productoRepository.save(producto);
            return "LOGICA";
        }
    }
    
    public Producto activarProducto(Long id) {
        Optional<Producto> producto = productoRepository.findById(id);
        if (producto.isPresent()) {
            Producto prod = producto.get();
            prod.setActivo(true);
            return productoRepository.save(prod);
        }
        throw new ProductoNotFoundException(id);
    }
}
