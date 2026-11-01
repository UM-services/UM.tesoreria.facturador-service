package um.tesoreria.facturador.hexagonal.facturacion.procesos.envio.application.usecases;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.application.service.ChequeraPagoService;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.application.service.FacturacionElectronicaService;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.domain.model.FacturacionElectronica;
import um.tesoreria.facturador.hexagonal.facturacion.gateways.recibo.application.service.ReciboService;
import um.tesoreria.facturador.hexagonal.facturacion.procesos.envio.domain.ports.in.EnviarReciboPorPagoUseCase;

@Component
@RequiredArgsConstructor
@Slf4j
public class EnviarReciboPorPagoUseCaseImpl implements EnviarReciboPorPagoUseCase {

    private final FacturacionElectronicaService facturacionElectronicaService;
    private final ChequeraPagoService chequeraPagoService;
    private final ReciboService reciboService;

    @Override
    public String enviar(Long chequeraPagoId) {
        log.debug("\n\nProcessing EnviarReciboPorPagoUseCaseImpl.enviar\n\n");
        FacturacionElectronica facturacionElectronica;
        try {
            facturacionElectronica = facturacionElectronicaService.getByChequeraPagoId(chequeraPagoId);
            /* Validacion heredada: el pago debe existir, aunque su resultado no se use. */
            chequeraPagoService.getById(chequeraPagoId);
        } catch (Exception e) {
            log.error("Facturacion pendiente");
            return "Facturación pendiente";
        }
        log.debug("FacturacionElectronica -> {}", facturacionElectronica);
        log.debug("\n\nEnviarReciboPorPagoUseCaseImpl.enviandoRecibo\n\n");
        return reciboService.enviar(facturacionElectronica.getFacturacionElectronicaId());
    }

}
