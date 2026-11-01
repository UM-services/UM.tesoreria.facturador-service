package um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.domain.ports.in;

import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.domain.model.ChequeraPago;

import java.time.OffsetDateTime;
import java.util.List;

public interface GetPendientesFacturaUseCase {

    List<ChequeraPago> getPendientes(OffsetDateTime fechaPago);

}
