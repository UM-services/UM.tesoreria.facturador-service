package um.tesoreria.facturador.hexagonal.facturacion.datos.comprobante.application.exception;

public class ComprobanteException extends RuntimeException {

    public ComprobanteException() {
        super("Could not find comprobante");
    }

    public ComprobanteException(Integer comprobanteId) {
        super("Could not find comprobante with id: " + comprobanteId);
    }

}
