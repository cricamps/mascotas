package mascotas.mascotas.exception;

public class ProductoNotFoundException extends RuntimeException {
    
    public ProductoNotFoundException(Long id) {
        super("No se encontro el producto con ID: " + id);
    }
    
    public ProductoNotFoundException(String mensaje) {
        super(mensaje);
    }
}
