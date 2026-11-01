package um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.domain.model.FacturacionElectronica;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.domain.ports.in.GetFacturacionElectronicaByIdUseCase;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.domain.ports.in.GetFacturacionElectronicaByPagoUseCase;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.domain.ports.in.GetFacturacionesPendientesUseCase;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.domain.ports.in.RegistrarFacturacionElectronicaUseCase;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacturacionElectronicaService {

    private final GetFacturacionElectronicaByPagoUseCase getFacturacionElectronicaByPagoUseCase;
    private final GetFacturacionElectronicaByIdUseCase getFacturacionElectronicaByIdUseCase;
    private final GetFacturacionesPendientesUseCase getFacturacionesPendientesUseCase;
    private final RegistrarFacturacionElectronicaUseCase registrarFacturacionElectronicaUseCase;

    public FacturacionElectronica getByChequeraPagoId(Long chequeraPagoId) {
        return getFacturacionElectronicaByPagoUseCase.getByChequeraPagoId(chequeraPagoId);
    }

    public FacturacionElectronica getById(Long facturacionElectronicaId) {
        return getFacturacionElectronicaByIdUseCase.getById(facturacionElectronicaId);
    }

    public List<FacturacionElectronica> getPendientes() {
        return getFacturacionesPendientesUseCase.getPendientes();
    }

    public FacturacionElectronica registrar(FacturacionElectronica facturacionElectronica) {
        return registrarFacturacionElectronicaUseCase.registrar(facturacionElectronica);
    }

}
