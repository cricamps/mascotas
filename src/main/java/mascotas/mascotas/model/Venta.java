package mascotas.mascotas.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Venta {
    private Long id;
    private Long productId;
    private String nombreProducto;
    private Integer cantidad;
    private Double precioUnitario;
    private Double total;
    private LocalDateTime fechaVenta;
    
    private static final NumberFormat formatoNumero = DecimalFormat.getInstance(new Locale("es", "CL"));
    private static final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    // Constructor por defecto
    public Venta() {
        this.fechaVenta = LocalDateTime.now();
    }

    // Constructor completo
    public Venta(Long id, Long productId, String nombreProducto, Integer cantidad, 
                Double precioUnitario, LocalDateTime fechaVenta) {
        this.id = id;
        this.productId = productId;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.fechaVenta = fechaVenta;
        this.total = cantidad * precioUnitario;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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
        recalcularTotal();
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }
    
    // Getter para precio unitario formateado
    public String getPrecioUnitarioFormateado() {
        return precioUnitario != null ? formatoNumero.format(precioUnitario) : "0";
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
        recalcularTotal();
    }

    public Double getTotal() {
        return total;
    }
    
    // Getter para total formateado
    public String getTotalFormateado() {
        return total != null ? formatoNumero.format(total) : "0";
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public LocalDateTime getFechaVenta() {
        return fechaVenta;
    }
    
    // Getter para fecha formateada
    public String getFechaVentaFormateada() {
        return fechaVenta != null ? fechaVenta.format(formatoFecha) : "";
    }

    public void setFechaVenta(LocalDateTime fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    private void recalcularTotal() {
        if (cantidad != null && precioUnitario != null) {
            this.total = cantidad * precioUnitario;
        }
    }
}
