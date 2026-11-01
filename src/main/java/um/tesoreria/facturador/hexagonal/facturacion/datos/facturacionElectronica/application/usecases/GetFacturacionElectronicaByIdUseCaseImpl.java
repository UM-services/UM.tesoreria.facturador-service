package um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.application.exception.FacturacionElectronicaException;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.domain.model.FacturacionElectronica;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.domain.ports.in.GetFacturacionElectronicaByIdUseCase;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.domain.ports.out.FacturacionElectronicaRepository;

@Component
@RequiredArgsConstructor
public class GetFacturacionElectronicaByIdUseCaseImpl implements GetFacturacionElectronicaByIdUseCase {

    private final FacturacionElectronicaRepository facturacionElectronicaRepository;

    @Override
    public FacturacionElectronica getById(Long facturacionElectronicaId) {
        return facturacionElectronicaRepository.findByFacturacionElectronicaId(facturacionElectronicaId)
                .orElseThrow(() -> new FacturacionElectronicaException(facturacionElectronicaId));
    }

}
