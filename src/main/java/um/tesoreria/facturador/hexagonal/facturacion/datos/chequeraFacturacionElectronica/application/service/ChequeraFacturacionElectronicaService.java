package um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraFacturacionElectronica.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraFacturacionElectronica.domain.model.ChequeraFacturacionElectronica;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraFacturacionElectronica.domain.ports.in.GetChequeraFacturacionElectronicaUseCase;

@Service
@RequiredArgsConstructor
public class ChequeraFacturacionElectronicaService {

    private final GetChequeraFacturacionElectronicaUseCase getChequeraFacturacionElectronicaUseCase;

    public ChequeraFacturacionElectronica getByChequeraId(Long chequeraId) {
        return getChequeraFacturacionElectronicaUseCase.getByChequeraId(chequeraId);
    }

}
