package um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraFacturacionElectronica.domain.ports.out;

import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraFacturacionElectronica.domain.model.ChequeraFacturacionElectronica;

import java.util.Optional;

public interface ChequeraFacturacionElectronicaRepository {

    Optional<ChequeraFacturacionElectronica> findByChequeraId(Long chequeraId);

}
