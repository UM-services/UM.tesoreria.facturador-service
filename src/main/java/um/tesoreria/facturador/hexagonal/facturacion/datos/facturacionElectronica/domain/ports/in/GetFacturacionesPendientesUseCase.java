package um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.domain.ports.in;

import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.domain.model.FacturacionElectronica;

import java.util.List;

public interface GetFacturacionesPendientesUseCase {

    List<FacturacionElectronica> getPendientes();

}
