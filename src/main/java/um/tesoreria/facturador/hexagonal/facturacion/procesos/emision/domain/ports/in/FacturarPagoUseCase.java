package um.tesoreria.facturador.hexagonal.facturacion.procesos.emision.domain.ports.in;

public interface FacturarPagoUseCase {

    String facturarPago(Long chequeraPagoId);

}
