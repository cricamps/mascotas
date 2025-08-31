package mascotas.mascotas.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class Producto {
    private Long id;
    private String nombre;
    private String categoria;
    private Double precio;
    private Integer stock;
    private String descripcion;
    private String marca;
    private String tipoMascota;
    
    private static final NumberFormat formatoNumero = DecimalFormat.getInstance(new Locale("es", "CL"));

    // Constructor por defecto
    public Producto() {
    }

    // Constructor completo
    public Producto(Long id, String nombre, String categoria, Double precio, Integer stock, 
                   String descripcion, String marca, String tipoMascota) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.descripcion = descripcion;
        this.marca = marca;
        this.tipoMascota = tipoMascota;
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

    public Double getPrecio() {
        return precio;
    }
    
    // Getter para precio formateado
    public String getPrecioFormateado() {
        return precio != null ? formatoNumero.format(precio) : "0";
    }

    public void setPrecio(Double precio) {
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

    // Método para verificar si tiene stock
    public boolean tieneStock() {
        return stock != null && stock > 0;
    }
}
