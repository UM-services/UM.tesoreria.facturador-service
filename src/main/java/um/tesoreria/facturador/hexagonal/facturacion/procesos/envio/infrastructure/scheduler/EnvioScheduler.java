package um.tesoreria.facturador.hexagonal.facturacion.procesos.envio.infrastructure.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import um.tesoreria.facturador.hexagonal.facturacion.procesos.envio.application.service.EnvioService;

@Component
@ConditionalOnProperty(name = "app.testing", havingValue = "false", matchIfMissing = true)
@RequiredArgsConstructor
public class EnvioScheduler {

    private final EnvioService envioService;

    @Scheduled(cron = "0 */5 * * * *")
    public void sendFacturasPendientesScheduled() {
        envioService.sendFacturasPendientes();
    }

}
