package um.tesoreria.facturador.hexagonal.facturacion.procesos.envio.application.usecases;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.application.service.FacturacionElectronicaService;
import um.tesoreria.facturador.hexagonal.facturacion.procesos.envio.domain.ports.in.EnviarFacturasPendientesUseCase;
import um.tesoreria.facturador.hexagonal.facturacion.procesos.envio.domain.ports.in.EnviarReciboPorFacturacionUseCase;

@Component
@RequiredArgsConstructor
@Slf4j
public class EnviarFacturasPendientesUseCaseImpl implements EnviarFacturasPendientesUseCase {

    private final FacturacionElectronicaService facturacionElectronicaService;
    private final EnviarReciboPorFacturacionUseCase enviarReciboPorFacturacionUseCase;

    @Override
    public void enviarPendientes() {
        log.info("Processing EnviarFacturasPendientesUseCaseImpl.enviarPendientes");
        for (var facturacionElectronica : facturacionElectronicaService.getPendientes()) {
            log.info("Sending from EnviarFacturasPendientesUseCaseImpl.enviarPendientes");
            log.debug("FacturacionElectronica -> {}", facturacionElectronica);
            enviarReciboPorFacturacionUseCase.enviar(facturacionElectronica.getFacturacionElectronicaId());
        }
    }

}
