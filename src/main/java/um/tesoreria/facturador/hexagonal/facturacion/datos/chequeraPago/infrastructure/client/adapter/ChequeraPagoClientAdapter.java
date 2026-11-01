package um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.infrastructure.client.adapter;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.domain.model.ChequeraPago;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.domain.ports.out.ChequeraPagoRepository;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.infrastructure.client.ChequeraPagoClient;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.infrastructure.client.mapper.ChequeraPagoMapper;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ChequeraPagoClientAdapter implements ChequeraPagoRepository {

    private final ChequeraPagoClient chequeraPagoClient;
    private final ChequeraPagoMapper chequeraPagoMapper;

    @Override
    public List<ChequeraPago> findPendientesFactura(OffsetDateTime fechaPago) {
        return chequeraPagoClient.pendientesFactura(fechaPago).stream()
                .map(chequeraPagoMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<ChequeraPago> findByChequeraPagoId(Long chequeraPagoId) {
        try {
            return Optional.ofNullable(chequeraPagoMapper.toDomain(
                    chequeraPagoClient.findByChequeraPagoId(chequeraPagoId)));
        } catch (FeignException.NotFound e) {
            return Optional.empty();
        }
    }

}
