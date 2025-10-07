package mascotas.mascotas.service;

import mascotas.mascotas.model.Producto;
import mascotas.mascotas.repository.ProductoRepository;
import mascotas.mascotas.repository.VentaRepository;
import mascotas.mascotas.exception.ProductoNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests del Servicio de Productos")
public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private VentaRepository ventaRepository;

    @InjectMocks
    private ProductoService productoService;

    private Producto producto1;

    @BeforeEach
    public void setUp() {
        producto1 = new Producto();
        producto1.setId(1L);
        producto1.setNombre("Alimento Premium Perro");
        producto1.setCategoria("Alimento");
        producto1.setPrecio(new BigDecimal("15000"));
        producto1.setStock(50);
        producto1.setTipoMascota("Perro");
        producto1.setActivo(true);
    }

    @Test
    @DisplayName("Test 1: Crear producto correctamente")
    public void testCrearProducto() {
        when(productoRepository.save(any(Producto.class))).thenReturn(producto1);

        Producto resultado = productoService.crearProducto(producto1);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getNombre()).isEqualTo("Alimento Premium Perro");
        assertThat(resultado.getCategoria()).isEqualTo("Alimento");
        
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    @DisplayName("Test 2: Obtener todos los productos")
    public void testObtenerTodosLosProductos() {
        Producto producto2 = new Producto();
        producto2.setId(2L);
        producto2.setNombre("Juguete Gato");
        
        List<Producto> listaProductos = Arrays.asList(producto1, producto2);
        when(productoRepository.findAll()).thenReturn(listaProductos);

        List<Producto> resultado = productoService.obtenerTodosLosProductos();

        assertThat(resultado).isNotNull();
        assertThat(resultado).hasSize(2);
        
        verify(productoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test 3: Actualizar producto existente")
    public void testActualizarProductoExistente() {
        Producto productoActualizado = new Producto();
        productoActualizado.setNombre("Alimento Super Premium");
        productoActualizado.setCategoria("Alimento");
        productoActualizado.setPrecio(new BigDecimal("18000"));
        productoActualizado.setStock(60);
        productoActualizado.setTipoMascota("Perro");
        productoActualizado.setActivo(true);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto1));
        when(productoRepository.save(any(Producto.class))).thenReturn(producto1);

        Producto resultado = productoService.actualizarProducto(1L, productoActualizado);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getNombre()).isEqualTo("Alimento Super Premium");
        
        verify(productoRepository, times(1)).findById(1L);
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    @DisplayName("Test 4: Lanzar excepcion al actualizar producto no existente")
    public void testActualizarProductoNoExistente() {
        when(productoRepository.findById(999L)).thenReturn(Optional.empty());

        Producto productoActualizado = new Producto();
        productoActualizado.setNombre("Producto No Existe");

        assertThatThrownBy(() -> productoService.actualizarProducto(999L, productoActualizado))
                .isInstanceOf(ProductoNotFoundException.class);
        
        verify(productoRepository, times(1)).findById(999L);
        verify(productoRepository, never()).save(any(Producto.class));
    }
}
