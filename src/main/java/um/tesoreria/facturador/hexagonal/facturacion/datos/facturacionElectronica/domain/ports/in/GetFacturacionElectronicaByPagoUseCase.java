package um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.domain.ports.in;

import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.domain.model.FacturacionElectronica;

public interface GetFacturacionElectronicaByPagoUseCase {

    FacturacionElectronica getByChequeraPagoId(Long chequeraPagoId);

}
