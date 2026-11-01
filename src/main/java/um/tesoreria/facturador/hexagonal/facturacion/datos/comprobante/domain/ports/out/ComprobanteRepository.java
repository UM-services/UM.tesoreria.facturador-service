package um.tesoreria.facturador.hexagonal.facturacion.datos.comprobante.domain.ports.out;

import um.tesoreria.facturador.hexagonal.facturacion.datos.comprobante.domain.model.Comprobante;

import java.util.Optional;

public interface ComprobanteRepository {

    Optional<Comprobante> findByComprobanteId(Integer comprobanteId);

}
