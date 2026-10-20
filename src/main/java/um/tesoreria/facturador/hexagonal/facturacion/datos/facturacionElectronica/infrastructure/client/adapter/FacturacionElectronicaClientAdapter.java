package um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.infrastructure.client.adapter;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.domain.model.FacturacionElectronica;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.domain.ports.out.FacturacionElectronicaRepository;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.infrastructure.client.FacturacionElectronicaClient;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.infrastructure.client.mapper.FacturacionElectronicaMapper;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class FacturacionElectronicaClientAdapter implements FacturacionElectronicaRepository {

    private final FacturacionElectronicaClient facturacionElectronicaClient;
    private final FacturacionElectronicaMapper facturacionElectronicaMapper;

    @Override
    public Optional<FacturacionElectronica> findByChequeraPagoId(Long chequeraPagoId) {
        try {
            var response = facturacionElectronicaClient.findByChequeraPagoId(chequeraPagoId);
            log.debug("FacturacionElectronica -> {}", response == null ? null : response.jsonify());
            return Optional.ofNullable(facturacionElectronicaMapper.toDomain(response));
        } catch (FeignException.NotFound e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<FacturacionElectronica> findByFacturacionElectronicaId(Long facturacionElectronicaId) {
        try {
            var response = facturacionElectronicaClient.findByFacturacionElectronicaId(facturacionElectronicaId);
            log.debug("FacturacionElectronica -> {}", response == null ? null : response.jsonify());
            return Optional.ofNullable(facturacionElectronicaMapper.toDomain(response));
        } catch (FeignException.NotFound e) {
            return Optional.empty();
        }
    }

    @Override
    public List<FacturacionElectronica> findPendientes() {
        return facturacionElectronicaClient.find3Pendientes().stream()
                .map(facturacionElectronicaMapper::toDomain)
                .toList();
    }

    @Override
    public FacturacionElectronica registrar(FacturacionElectronica facturacionElectronica) {
        var request = facturacionElectronicaMapper.toDto(facturacionElectronica);
        log.debug("before add FacturacionElectronica");
        log.debug("FacturacionElectronica -> {}", request.jsonify());
        var response = facturacionElectronicaClient.add(request);
        log.debug("after add FacturacionElectronica");
        log.debug("FacturacionElectronica -> {}", response == null ? null : response.jsonify());
        return facturacionElectronicaMapper.toDomain(response);
    }

}
