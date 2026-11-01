package um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.domain.ports.in;

import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.domain.model.ChequeraPago;

public interface GetChequeraPagoUseCase {

    ChequeraPago getById(Long chequeraPagoId);

}
