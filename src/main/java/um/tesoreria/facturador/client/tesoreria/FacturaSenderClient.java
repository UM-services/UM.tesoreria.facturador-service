package um.tesoreria.facturador.client.tesoreria;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import um.tesoreria.facturador.kotlin.tesoreria.afip.dto.FacturacionDto;

@FeignClient(name = "factura-sender-service/api/facturaSender")
public interface FacturaSenderClient {

    @PostMapping("/send")
    FacturacionDto send(@RequestBody FacturacionDto facturacionDto);

}