package um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.domain.model.FacturacionElectronica;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.domain.ports.in.GetFacturacionesPendientesUseCase;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.domain.ports.out.FacturacionElectronicaRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetFacturacionesPendientesUseCaseImpl implements GetFacturacionesPendientesUseCase {

    private final FacturacionElectronicaRepository facturacionElectronicaRepository;

    @Override
    public List<FacturacionElectronica> getPendientes() {
        return facturacionElectronicaRepository.findPendientes();
    }

}
