package um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraFacturacionElectronica.infrastructure.client.adapter;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraFacturacionElectronica.domain.model.ChequeraFacturacionElectronica;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraFacturacionElectronica.domain.ports.out.ChequeraFacturacionElectronicaRepository;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraFacturacionElectronica.infrastructure.client.ChequeraFacturacionElectronicaClient;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraFacturacionElectronica.infrastructure.client.mapper.ChequeraFacturacionElectronicaMapper;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ChequeraFacturacionElectronicaClientAdapter implements ChequeraFacturacionElectronicaRepository {

    private final ChequeraFacturacionElectronicaClient chequeraFacturacionElectronicaClient;
    private final ChequeraFacturacionElectronicaMapper chequeraFacturacionElectronicaMapper;

    @Override
    public Optional<ChequeraFacturacionElectronica> findByChequeraId(Long chequeraId) {
        try {
            return Optional.ofNullable(chequeraFacturacionElectronicaMapper.toDomain(
                    chequeraFacturacionElectronicaClient.findByChequeraId(chequeraId)));
        } catch (FeignException.NotFound e) {
            return Optional.empty();
        }
    }

}
