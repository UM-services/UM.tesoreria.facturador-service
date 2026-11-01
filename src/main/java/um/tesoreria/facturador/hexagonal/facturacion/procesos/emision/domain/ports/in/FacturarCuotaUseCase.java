package um.tesoreria.facturador.hexagonal.facturacion.procesos.emision.domain.ports.in;

import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.domain.model.ChequeraPago;

public interface FacturarCuotaUseCase {

    boolean facturarCuota(ChequeraPago chequeraPago);

}
