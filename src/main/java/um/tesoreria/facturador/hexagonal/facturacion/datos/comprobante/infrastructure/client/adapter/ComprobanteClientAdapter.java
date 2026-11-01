package um.tesoreria.facturador.hexagonal.facturacion.datos.comprobante.infrastructure.client.adapter;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import um.tesoreria.facturador.hexagonal.facturacion.datos.comprobante.domain.model.Comprobante;
import um.tesoreria.facturador.hexagonal.facturacion.datos.comprobante.domain.ports.out.ComprobanteRepository;
import um.tesoreria.facturador.hexagonal.facturacion.datos.comprobante.infrastructure.client.ComprobanteClient;
import um.tesoreria.facturador.hexagonal.facturacion.datos.comprobante.infrastructure.client.mapper.ComprobanteMapper;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ComprobanteClientAdapter implements ComprobanteRepository {

    private final ComprobanteClient comprobanteClient;
    private final ComprobanteMapper comprobanteMapper;

    @Override
    public Optional<Comprobante> findByComprobanteId(Integer comprobanteId) {
        try {
            return Optional.ofNullable(comprobanteMapper.toDomain(
                    comprobanteClient.findByComprobanteId(comprobanteId)));
        } catch (FeignException.NotFound e) {
            return Optional.empty();
        }
    }

}
