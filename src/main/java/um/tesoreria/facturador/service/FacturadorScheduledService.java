package um.tesoreria.facturador.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.testing", havingValue = "false", matchIfMissing = true)
@RequiredArgsConstructor
public class FacturadorScheduledService {

    private final FacturadorService service;

    @Scheduled(cron = "0 0 2 * * *")
    public void facturaPendientesScheduled() {
        service.facturaPendientes();
    }

    @Scheduled(cron = "0 */5 * * * *")
    public void sendFacturasPendientesScheduled() {
        service.sendFacturasPendientes();
    }

}
