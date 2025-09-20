package mascotas.mascotas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "VENTAS")
public class Venta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "venta_seq")
    @SequenceGenerator(name = "venta_seq", sequenceName = "VENTA_SEQ", allocationSize = 1)
    private Long id;
    
    @NotNull(message = "El ID del producto es obligatorio")
    @Column(name = "PRODUCTO_ID", nullable = false)
    private Long productoId;
    
    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 100, message = "El nombre del producto no puede tener más de 100 caracteres")
    @Column(name = "NOMBRE_PRODUCTO", nullable = false, length = 100)
    private String nombreProducto;
    
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor que 0")
    @Column(name = "CANTIDAD", nullable = false)
    private Integer cantidad;
    
    @NotNull(message = "El precio unitario es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    @Column(name = "PRECIO_UNITARIO", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;
    
    @NotNull(message = "El total es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El total debe ser mayor que 0")
    @Column(name = "TOTAL", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;
    
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "FECHA_VENTA", nullable = false, updatable = false)
    private LocalDateTime fechaVenta;

    // Constructor por defecto
    public Venta() {
    }

    // Constructor completo
    public Venta(Long productoId, String nombreProducto, Integer cantidad, BigDecimal precioUnitario) {
        this.productoId = productoId;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.total = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
        this.fechaVenta = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        if (fechaVenta == null) {
            fechaVenta = LocalDateTime.now();
        }
        if (total == null && precioUnitario != null && cantidad != null) {
            total = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
        }
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
        // Recalcular total si hay precio unitario
        if (this.precioUnitario != null) {
            this.total = this.precioUnitario.multiply(BigDecimal.valueOf(cantidad));
        }
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
        // Recalcular total si hay cantidad
        if (this.cantidad != null) {
            this.total = precioUnitario.multiply(BigDecimal.valueOf(this.cantidad));
        }
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public LocalDateTime getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDateTime fechaVenta) {
        this.fechaVenta = fechaVenta;
    }
}
