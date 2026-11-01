package um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.application.exception.FacturacionElectronicaException;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.domain.model.FacturacionElectronica;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.domain.ports.in.GetFacturacionElectronicaByPagoUseCase;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.domain.ports.out.FacturacionElectronicaRepository;

@Component
@RequiredArgsConstructor
public class GetFacturacionElectronicaByPagoUseCaseImpl implements GetFacturacionElectronicaByPagoUseCase {

    private final FacturacionElectronicaRepository facturacionElectronicaRepository;

    @Override
    public FacturacionElectronica getByChequeraPagoId(Long chequeraPagoId) {
        return facturacionElectronicaRepository.findByChequeraPagoId(chequeraPagoId)
                .orElseThrow(FacturacionElectronicaException::new);
    }

}
