package mascotas.mascotas.exception;

public class VentaNotFoundException extends RuntimeException {
    
    public VentaNotFoundException(Long id) {
        super("No se encontro la venta con ID: " + id);
    }
    
    public VentaNotFoundException(String mensaje) {
        super(mensaje);
    }
}
