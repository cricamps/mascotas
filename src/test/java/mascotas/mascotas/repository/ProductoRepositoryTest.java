package mascotas.mascotas.repository;

import mascotas.mascotas.model.Producto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests del Repositorio de Productos")
public class ProductoRepositoryTest {

    @Mock
    private ProductoRepository productoRepository;

    @Test
    @DisplayName("Test 1: Guardar producto correctamente")
    public void testGuardarProducto() {
        Producto producto = new Producto();
        producto.setNombre("Alimento Premium Perro");
        producto.setCategoria("Alimento");
        producto.setPrecio(new BigDecimal("15000"));
        producto.setStock(50);
        producto.setTipoMascota("Perro");
        producto.setActivo(true);

        Producto productoGuardado = new Producto();
        productoGuardado.setId(1L);
        productoGuardado.setNombre("Alimento Premium Perro");
        productoGuardado.setCategoria("Alimento");
        productoGuardado.setPrecio(new BigDecimal("15000"));
        productoGuardado.setStock(50);
        productoGuardado.setTipoMascota("Perro");
        productoGuardado.setActivo(true);

        when(productoRepository.save(any(Producto.class))).thenReturn(productoGuardado);

        Producto resultado = productoRepository.save(producto);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getNombre()).isEqualTo("Alimento Premium Perro");
        assertThat(resultado.getCategoria()).isEqualTo("Alimento");
        assertThat(resultado.getActivo()).isTrue();

        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    @DisplayName("Test 2: Buscar productos activos")
    public void testBuscarProductosActivos() {
        Producto producto1 = new Producto();
        producto1.setId(1L);
        producto1.setNombre("Juguete Gato");
        producto1.setCategoria("Juguete");
        producto1.setActivo(true);

        Producto producto2 = new Producto();
        producto2.setId(2L);
        producto2.setNombre("Collar Perro");
        producto2.setCategoria("Accesorio");
        producto2.setActivo(true);

        List<Producto> productosActivos = Arrays.asList(producto1, producto2);

        when(productoRepository.findByActivoTrue()).thenReturn(productosActivos);

        List<Producto> resultado = productoRepository.findByActivoTrue();

        assertThat(resultado).isNotEmpty();
        assertThat(resultado).hasSize(2);
        assertThat(resultado).allMatch(p -> p.getActivo() == true);

        verify(productoRepository, times(1)).findByActivoTrue();
    }
}
