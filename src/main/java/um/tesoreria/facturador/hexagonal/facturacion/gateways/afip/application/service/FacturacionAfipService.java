package um.tesoreria.facturador.hexagonal.facturacion.gateways.afip.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import um.tesoreria.facturador.hexagonal.facturacion.gateways.afip.domain.model.RespuestaFacturacionAfip;
import um.tesoreria.facturador.hexagonal.facturacion.gateways.afip.domain.model.SolicitudFacturacionAfip;
import um.tesoreria.facturador.hexagonal.facturacion.gateways.afip.domain.ports.in.FacturarAfipUseCase;
import um.tesoreria.facturador.hexagonal.facturacion.gateways.afip.domain.ports.in.TestAfipUseCase;

@Service
@RequiredArgsConstructor
public class FacturacionAfipService {

    private final TestAfipUseCase testAfipUseCase;
    private final FacturarAfipUseCase facturarAfipUseCase;

    public String test() {
        return testAfipUseCase.test();
    }

    public RespuestaFacturacionAfip facturar(SolicitudFacturacionAfip solicitud) {
        return facturarAfipUseCase.facturar(solicitud);
    }

}
