package um.tesoreria.facturador.hexagonal.facturacion.datos.comprobante.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import um.tesoreria.facturador.hexagonal.facturacion.datos.comprobante.infrastructure.client.dto.ComprobanteResponse;

@FeignClient(name = "tesoreria-core-service/api/tesoreria/core/comprobante")
public interface ComprobanteClient {

    @GetMapping("/{comprobanteId}")
    ComprobanteResponse findByComprobanteId(@PathVariable Integer comprobanteId);

}
