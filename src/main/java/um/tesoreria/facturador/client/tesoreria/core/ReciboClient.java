package um.tesoreria.facturador.client.tesoreria.core;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "tesoreria-sender-service/api/tesoreria/sender/recibo")
public interface ReciboClient {

    @GetMapping("/send/{facturacionElectronicaId}")
    String send(@PathVariable Long facturacionElectronicaId);

}
