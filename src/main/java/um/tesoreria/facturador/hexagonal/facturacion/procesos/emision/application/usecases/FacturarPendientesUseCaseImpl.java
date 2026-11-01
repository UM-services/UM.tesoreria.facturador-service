package um.tesoreria.facturador.hexagonal.facturacion.procesos.emision.application.usecases;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.application.service.ChequeraPagoService;
import um.tesoreria.facturador.hexagonal.facturacion.procesos.emision.domain.ports.in.FacturarCuotaUseCase;
import um.tesoreria.facturador.hexagonal.facturacion.procesos.emision.domain.ports.in.FacturarPendientesUseCase;

import java.time.LocalTime;
import java.time.OffsetDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class FacturarPendientesUseCaseImpl implements FacturarPendientesUseCase {

    private final ChequeraPagoService chequeraPagoService;
    private final FacturarCuotaUseCase facturarCuotaUseCase;

    @Override
    public String facturarPendientes() {
        log.debug("Processing FacturarPendientesUseCaseImpl.facturarPendientes");
        OffsetDateTime now = OffsetDateTime.now().with(LocalTime.MIDNIGHT);
        OffsetDateTime startDate = now.minusDays(90);
        OffsetDateTime endDate = now.plusDays(2);
        for (OffsetDateTime fechaPago = startDate; fechaPago.isBefore(endDate); fechaPago = fechaPago.plusDays(1)) {
            log.info("Procesando Fecha de Pago: {}", fechaPago);
            for (var chequeraPago : chequeraPagoService.getPendientes(fechaPago)) {
                if (facturarCuotaUseCase.facturarCuota(chequeraPago)) {
                    log.info("Facturado Ok");
                } else {
                    log.info("Facturado NO");
                }
            }
        }
        return "Fin proceso";
    }

}
