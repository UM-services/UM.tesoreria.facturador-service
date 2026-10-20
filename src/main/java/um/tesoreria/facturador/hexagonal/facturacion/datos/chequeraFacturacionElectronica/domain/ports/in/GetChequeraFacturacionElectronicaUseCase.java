package um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraFacturacionElectronica.domain.ports.in;

import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraFacturacionElectronica.domain.model.ChequeraFacturacionElectronica;

public interface GetChequeraFacturacionElectronicaUseCase {

    ChequeraFacturacionElectronica getByChequeraId(Long chequeraId);

}
