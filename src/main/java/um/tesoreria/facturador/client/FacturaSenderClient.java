package um.tesoreria.facturador.client;

import um.tesoreria.facturador.kotlin.model.dto.FacturacionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import um.tesoreria.facturador.kotlin.model.dto.FacturacionDto;

@FeignClient(name = "factura-sender-service/api/facturaSender")
public interface FacturaSenderClient {

    @PostMapping("/send")
    FacturacionDto send(@RequestBody FacturacionDto facturacionDto);

}
