package um.tesoreria.facturador.hexagonal.facturacion.procesos.emision.application.exception;

public class FacturacionException extends RuntimeException {

    public FacturacionException() {
        super("No se pudo facturar la cuota");
    }

    public FacturacionException(Long chequeraPagoId) {
        super("No se pudo facturar la cuota del pago: " + chequeraPagoId);
    }

}
