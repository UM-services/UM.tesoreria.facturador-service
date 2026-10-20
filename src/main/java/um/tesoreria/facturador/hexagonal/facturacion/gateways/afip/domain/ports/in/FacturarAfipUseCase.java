package um.tesoreria.facturador.hexagonal.facturacion.gateways.afip.domain.ports.in;

import um.tesoreria.facturador.hexagonal.facturacion.gateways.afip.domain.model.RespuestaFacturacionAfip;
import um.tesoreria.facturador.hexagonal.facturacion.gateways.afip.domain.model.SolicitudFacturacionAfip;

public interface FacturarAfipUseCase {

    RespuestaFacturacionAfip facturar(SolicitudFacturacionAfip solicitud);

}
