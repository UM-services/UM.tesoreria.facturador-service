package um.tesoreria.facturador.hexagonal.facturacion.gateways.afip.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import um.tesoreria.facturador.hexagonal.facturacion.gateways.afip.infrastructure.client.dto.FacturacionAfipDto;

@FeignClient("pyafipws-service/api/afipws")
public interface FacturacionAfipClient {

    @GetMapping("/test")
    String test();

    @PostMapping("/facturador")
    FacturacionAfipDto facturador(FacturacionAfipDto facturacionDto);

}
