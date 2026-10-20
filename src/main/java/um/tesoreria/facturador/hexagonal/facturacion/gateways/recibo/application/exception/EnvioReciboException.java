package um.tesoreria.facturador.hexagonal.facturacion.gateways.recibo.application.exception;

public class EnvioReciboException extends RuntimeException {

    public EnvioReciboException(Long facturacionElectronicaId, Throwable cause) {
        super("No se pudo enviar el recibo " + facturacionElectronicaId
                + ". Motivo: " + (cause == null ? "" : cause.getMessage()), cause);
    }

}
