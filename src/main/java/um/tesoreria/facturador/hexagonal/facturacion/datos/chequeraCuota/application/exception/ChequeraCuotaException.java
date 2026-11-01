package um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraCuota.application.exception;

public class ChequeraCuotaException extends RuntimeException {

    public ChequeraCuotaException() {
        super("Could not find chequera cuota");
    }

    public ChequeraCuotaException(Long chequeraSerieId, Integer cuotaId) {
        super("Could not find chequera cuota for serie " + chequeraSerieId + " and cuota " + cuotaId);
    }

}
