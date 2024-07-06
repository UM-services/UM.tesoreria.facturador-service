package ar.edu.um.tesoreria.facturador.client;

import ar.edu.um.tesoreria.facturador.kotlin.model.dto.ComprobanteDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "core-service/comprobante")
public interface ComprobanteClient {

    @GetMapping("/{comprobanteId}")
    ComprobanteDto findByComprobanteId(@PathVariable Integer comprobanteId);

}
