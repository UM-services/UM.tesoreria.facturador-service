package um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.application.exception;

public class FacturacionElectronicaException extends RuntimeException {

    public FacturacionElectronicaException() {
        super("Could not find facturacion electronica");
    }

    public FacturacionElectronicaException(Long id) {
        super("Could not find facturacion electronica with id: " + id);
    }

}
