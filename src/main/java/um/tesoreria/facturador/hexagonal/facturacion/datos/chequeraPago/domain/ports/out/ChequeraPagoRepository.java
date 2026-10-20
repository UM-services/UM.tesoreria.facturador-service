package um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.domain.ports.out;

import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.domain.model.ChequeraPago;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface ChequeraPagoRepository {

    List<ChequeraPago> findPendientesFactura(OffsetDateTime fechaPago);

    Optional<ChequeraPago> findByChequeraPagoId(Long chequeraPagoId);

}
