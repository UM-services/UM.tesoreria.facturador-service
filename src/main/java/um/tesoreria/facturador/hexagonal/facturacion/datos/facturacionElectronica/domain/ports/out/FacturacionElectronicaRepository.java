package um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.domain.ports.out;

import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.domain.model.FacturacionElectronica;

import java.util.List;
import java.util.Optional;

public interface FacturacionElectronicaRepository {

    Optional<FacturacionElectronica> findByChequeraPagoId(Long chequeraPagoId);

    Optional<FacturacionElectronica> findByFacturacionElectronicaId(Long facturacionElectronicaId);

    List<FacturacionElectronica> findPendientes();

    FacturacionElectronica registrar(FacturacionElectronica facturacionElectronica);

}
