package um.tesoreria.facturador.hexagonal.facturacion.gateways.afip.infrastructure.client.adapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import um.tesoreria.facturador.hexagonal.facturacion.gateways.afip.domain.model.RespuestaFacturacionAfip;
import um.tesoreria.facturador.hexagonal.facturacion.gateways.afip.domain.model.SolicitudFacturacionAfip;
import um.tesoreria.facturador.hexagonal.facturacion.gateways.afip.domain.ports.out.FacturacionAfipRepository;
import um.tesoreria.facturador.hexagonal.facturacion.gateways.afip.infrastructure.client.FacturacionAfipClient;
import um.tesoreria.facturador.hexagonal.facturacion.gateways.afip.infrastructure.client.mapper.FacturacionAfipMapper;

@Component
@RequiredArgsConstructor
@Slf4j
public class FacturacionAfipClientAdapter implements FacturacionAfipRepository {

    private final FacturacionAfipClient facturacionAfipClient;
    private final FacturacionAfipMapper facturacionAfipMapper;

    @Override
    public String test() {
        return facturacionAfipClient.test();
    }

    @Override
    public RespuestaFacturacionAfip facturar(SolicitudFacturacionAfip solicitud) {
        var request = facturacionAfipMapper.toDto(solicitud);
        log.debug("Facturacion -> {}", request.jsonify());
        var response = facturacionAfipClient.facturador(request);
        log.debug("Facturacion -> {}", response == null ? null : response.jsonify());
        return facturacionAfipMapper.toDomain(response);
    }

}
