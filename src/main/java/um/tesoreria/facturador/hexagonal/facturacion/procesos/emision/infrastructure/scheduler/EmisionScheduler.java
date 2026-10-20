package um.tesoreria.facturador.hexagonal.facturacion.procesos.emision.infrastructure.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import um.tesoreria.facturador.hexagonal.facturacion.procesos.emision.application.service.EmisionService;

@Component
@ConditionalOnProperty(name = "app.testing", havingValue = "false", matchIfMissing = true)
@RequiredArgsConstructor
public class EmisionScheduler {

    private final EmisionService emisionService;

    @Scheduled(cron = "0 0 2 * * *")
    public void facturaPendientesScheduled() {
        emisionService.facturaPendientes();
    }

}
