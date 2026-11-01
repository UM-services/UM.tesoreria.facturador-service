package um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraFacturacionElectronica.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraFacturacionElectronica.application.exception.ChequeraFacturacionElectronicaException;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraFacturacionElectronica.domain.model.ChequeraFacturacionElectronica;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraFacturacionElectronica.domain.ports.in.GetChequeraFacturacionElectronicaUseCase;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraFacturacionElectronica.domain.ports.out.ChequeraFacturacionElectronicaRepository;

@Component
@RequiredArgsConstructor
public class GetChequeraFacturacionElectronicaUseCaseImpl implements GetChequeraFacturacionElectronicaUseCase {

    private final ChequeraFacturacionElectronicaRepository chequeraFacturacionElectronicaRepository;

    @Override
    public ChequeraFacturacionElectronica getByChequeraId(Long chequeraId) {
        return chequeraFacturacionElectronicaRepository.findByChequeraId(chequeraId)
                .orElseThrow(() -> new ChequeraFacturacionElectronicaException(chequeraId));
    }

}
