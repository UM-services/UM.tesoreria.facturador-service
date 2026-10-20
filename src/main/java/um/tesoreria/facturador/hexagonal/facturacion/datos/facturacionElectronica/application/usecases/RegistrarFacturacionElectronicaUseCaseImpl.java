package um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.domain.model.FacturacionElectronica;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.domain.ports.in.RegistrarFacturacionElectronicaUseCase;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.domain.ports.out.FacturacionElectronicaRepository;

@Component
@RequiredArgsConstructor
public class RegistrarFacturacionElectronicaUseCaseImpl implements RegistrarFacturacionElectronicaUseCase {

    private final FacturacionElectronicaRepository facturacionElectronicaRepository;

    @Override
    public FacturacionElectronica registrar(FacturacionElectronica facturacionElectronica) {
        return facturacionElectronicaRepository.registrar(facturacionElectronica);
    }

}
