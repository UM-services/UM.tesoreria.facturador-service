package um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraCuota.infrastructure.client.adapter;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraCuota.domain.model.ChequeraCuota;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraCuota.domain.ports.out.ChequeraCuotaRepository;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraCuota.infrastructure.client.ChequeraCuotaClient;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraCuota.infrastructure.client.mapper.ChequeraCuotaMapper;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ChequeraCuotaClientAdapter implements ChequeraCuotaRepository {

    private final ChequeraCuotaClient chequeraCuotaClient;
    private final ChequeraCuotaMapper chequeraCuotaMapper;

    @Override
    public Optional<ChequeraCuota> findByUnique(Integer facultadId,
                                                Integer tipoChequeraId,
                                                Long chequeraSerieId,
                                                Integer productoId,
                                                Integer alternativaId,
                                                Integer cuotaId) {
        try {
            return Optional.ofNullable(chequeraCuotaMapper.toDomain(
                    chequeraCuotaClient.findByUnique(facultadId, tipoChequeraId, chequeraSerieId,
                            productoId, alternativaId, cuotaId)));
        } catch (FeignException.NotFound e) {
            return Optional.empty();
        }
    }

}
