package um.tesoreria.facturador.hexagonal.facturacion.procesos.envio.application.usecases;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.application.service.ChequeraPagoService;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.application.service.FacturacionElectronicaService;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.domain.model.FacturacionElectronica;
import um.tesoreria.facturador.hexagonal.facturacion.gateways.recibo.application.exception.EnvioReciboException;
import um.tesoreria.facturador.hexagonal.facturacion.gateways.recibo.application.service.ReciboService;
import um.tesoreria.facturador.hexagonal.facturacion.procesos.envio.domain.ports.in.EnviarReciboPorFacturacionUseCase;

@Component
@RequiredArgsConstructor
@Slf4j
public class EnviarReciboPorFacturacionUseCaseImpl implements EnviarReciboPorFacturacionUseCase {

    private final FacturacionElectronicaService facturacionElectronicaService;
    private final ChequeraPagoService chequeraPagoService;
    private final ReciboService reciboService;

    @Override
    public String enviar(Long facturacionElectronicaId) {
        log.debug("Processing EnviarReciboPorFacturacionUseCaseImpl.enviar");
        FacturacionElectronica facturacionElectronica;
        try {
            facturacionElectronica = facturacionElectronicaService.getById(facturacionElectronicaId);
            log.debug("FacturacionElectronica -> {}", facturacionElectronica);
            var chequeraPago = chequeraPagoService.getById(facturacionElectronica.getChequeraPagoId());
            log.debug("ChequeraPago -> {}", chequeraPago);
        } catch (Exception e) {
            log.debug("\n\nError enviando recibo -> {}\n\n", e.getMessage());
            return "Facturación pendiente";
        }
        log.debug("EnviarReciboPorFacturacionUseCaseImpl.enviandoRecibo");
        try {
            return reciboService.enviar(facturacionElectronicaId);
        } catch (EnvioReciboException e) {
            log.error("No se pudo enviar el recibo {}. Motivo: {}", facturacionElectronicaId, e.getMessage());
            return "Error de envío";
        }
    }

}
