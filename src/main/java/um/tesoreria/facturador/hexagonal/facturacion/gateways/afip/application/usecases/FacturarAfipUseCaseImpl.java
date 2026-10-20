package um.tesoreria.facturador.hexagonal.facturacion.gateways.afip.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import um.tesoreria.facturador.hexagonal.facturacion.gateways.afip.domain.model.RespuestaFacturacionAfip;
import um.tesoreria.facturador.hexagonal.facturacion.gateways.afip.domain.model.SolicitudFacturacionAfip;
import um.tesoreria.facturador.hexagonal.facturacion.gateways.afip.domain.ports.in.FacturarAfipUseCase;
import um.tesoreria.facturador.hexagonal.facturacion.gateways.afip.domain.ports.out.FacturacionAfipRepository;

@Component
@RequiredArgsConstructor
public class FacturarAfipUseCaseImpl implements FacturarAfipUseCase {

    private final FacturacionAfipRepository facturacionAfipRepository;

    @Override
    public RespuestaFacturacionAfip facturar(SolicitudFacturacionAfip solicitud) {
        return facturacionAfipRepository.facturar(solicitud);
    }

}
