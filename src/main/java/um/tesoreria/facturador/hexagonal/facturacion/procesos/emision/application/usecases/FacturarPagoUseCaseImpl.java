package um.tesoreria.facturador.hexagonal.facturacion.procesos.emision.application.usecases;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.application.service.ChequeraPagoService;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.application.service.FacturacionElectronicaService;
import um.tesoreria.facturador.hexagonal.facturacion.procesos.emision.domain.ports.in.FacturarCuotaUseCase;
import um.tesoreria.facturador.hexagonal.facturacion.procesos.emision.domain.ports.in.FacturarPagoUseCase;

@Component
@RequiredArgsConstructor
@Slf4j
public class FacturarPagoUseCaseImpl implements FacturarPagoUseCase {

    private final FacturacionElectronicaService facturacionElectronicaService;
    private final ChequeraPagoService chequeraPagoService;
    private final FacturarCuotaUseCase facturarCuotaUseCase;

    @Override
    public String facturarPago(Long chequeraPagoId) {
        log.debug("Processing FacturarPagoUseCaseImpl.facturarPago");
        try {
            var facturacionElectronica = facturacionElectronicaService.getByChequeraPagoId(chequeraPagoId);
            log.debug("FacturacionElectronica -> {}", facturacionElectronica);
            return "ERROR: Facturación previa";
        } catch (Exception e) {
            log.debug("Facturacion pendiente");
        }

        var chequeraPago = chequeraPagoService.getById(chequeraPagoId);
        log.debug("ChequeraPago -> {}", chequeraPago);

        if (facturarCuotaUseCase.facturarCuota(chequeraPago)) {
            log.info("Facturado Ok");
            return "Facturado Ok";
        }
        return "Facturado NO";
    }

}
