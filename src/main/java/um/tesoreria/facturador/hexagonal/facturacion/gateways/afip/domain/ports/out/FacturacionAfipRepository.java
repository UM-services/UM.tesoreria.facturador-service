package um.tesoreria.facturador.hexagonal.facturacion.gateways.afip.domain.ports.out;

import um.tesoreria.facturador.hexagonal.facturacion.gateways.afip.domain.model.RespuestaFacturacionAfip;
import um.tesoreria.facturador.hexagonal.facturacion.gateways.afip.domain.model.SolicitudFacturacionAfip;

public interface FacturacionAfipRepository {

    String test();

    RespuestaFacturacionAfip facturar(SolicitudFacturacionAfip solicitud);

}
