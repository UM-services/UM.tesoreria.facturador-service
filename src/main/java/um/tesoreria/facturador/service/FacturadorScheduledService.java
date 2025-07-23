package um.tesoreria.facturador.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.testing", havingValue = "false", matchIfMissing = true)
public class FacturadorScheduledService {

    private final FacturadorService service;

    public FacturadorScheduledService(FacturadorService service) {
        this.service = service;
    }

    @Scheduled(cron = "0 0 2 * * *")
    public void facturaPendientesScheduled() {
        service.facturaPendientes();
    }

    @Scheduled(cron = "0 * * * * *")
    public void sendFacturasPendientesScheduled() {
        service.sendFacturasPendientes();
    }

}
