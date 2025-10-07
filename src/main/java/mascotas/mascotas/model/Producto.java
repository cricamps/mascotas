package mascotas.mascotas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PRODUCTOS")
public class Producto extends RepresentationModel<Producto> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "producto_seq")
    @SequenceGenerator(name = "producto_seq", sequenceName = "PRODUCTO_SEQ", allocationSize = 1)
    private Long id;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede tener mas de 100 caracteres")
    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;
    
    @NotBlank(message = "La categoria es obligatoria")
    @Size(max = 50, message = "La categoria no puede tener mas de 50 caracteres")
    @Column(name = "CATEGORIA", nullable = false, length = 50)
    private String categoria;
    
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    @Column(name = "PRECIO", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;
    
    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Column(name = "STOCK", nullable = false)
    private Integer stock;
    
    @Size(max = 500, message = "La descripcion no puede tener mas de 500 caracteres")
    @Column(name = "DESCRIPCION", length = 500)
    private String descripcion;
    
    @Size(max = 50, message = "La marca no puede tener mas de 50 caracteres")
    @Column(name = "MARCA", length = 50)
    private String marca;
    
    @NotBlank(message = "El tipo de mascota es obligatorio")
    @Size(max = 30, message = "El tipo de mascota no puede tener mas de 30 caracteres")
    @Column(name = "TIPO_MASCOTA", nullable = false, length = 30)
    private String tipoMascota;
    
    @Column(name = "ACTIVO", nullable = false)
    private Boolean activo = true;
    
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "FECHA_REGISTRO", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;
    
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "FECHA_ACTUALIZACION")
    private LocalDateTime fechaActualizacion;

    // Constructor por defecto
    public Producto() {
    }

    // Constructor completo
    public Producto(String nombre, String categoria, BigDecimal precio, Integer stock, 
                   String descripcion, String marca, String tipoMascota) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.descripcion = descripcion;
        this.marca = marca;
        this.tipoMascota = tipoMascota;
        this.activo = true;
    }

    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
        if (activo == null) {
            activo = true;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTipoMascota() {
        return tipoMascota;
    }

    public void setTipoMascota(String tipoMascota) {
        this.tipoMascota = tipoMascota;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    // Metodos de utilidad
    public boolean tieneStock() {
        return stock != null && stock > 0;
    }

    public boolean isActivo() {
        return activo != null && activo;
    }
}
