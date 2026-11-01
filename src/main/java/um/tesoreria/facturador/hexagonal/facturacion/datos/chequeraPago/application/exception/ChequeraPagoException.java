package um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.application.exception;

public class ChequeraPagoException extends RuntimeException {

    public ChequeraPagoException() {
        super("Could not find chequera pago");
    }

    public ChequeraPagoException(Long chequeraPagoId) {
        super("Could not find chequera pago with id: " + chequeraPagoId);
    }

}
