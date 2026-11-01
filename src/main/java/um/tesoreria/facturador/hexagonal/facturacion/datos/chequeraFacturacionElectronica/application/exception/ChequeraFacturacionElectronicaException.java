package um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraFacturacionElectronica.application.exception;

public class ChequeraFacturacionElectronicaException extends RuntimeException {

    public ChequeraFacturacionElectronicaException() {
        super("Could not find chequera facturacion electronica");
    }

    public ChequeraFacturacionElectronicaException(Long chequeraId) {
        super("Could not find chequera facturacion electronica with chequeraId: " + chequeraId);
    }

}
