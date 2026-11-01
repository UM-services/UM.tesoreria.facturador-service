package um.tesoreria.facturador.hexagonal.facturacion.datos.comprobante.domain.ports.in;

import um.tesoreria.facturador.hexagonal.facturacion.datos.comprobante.domain.model.Comprobante;

public interface GetComprobanteUseCase {

    Comprobante getById(Integer comprobanteId);

}
