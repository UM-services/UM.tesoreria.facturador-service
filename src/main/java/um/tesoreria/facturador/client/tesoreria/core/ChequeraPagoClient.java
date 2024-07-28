package um.tesoreria.facturador.client.tesoreria.core;

import um.tesoreria.facturador.kotlin.tesoreria.core.dto.ChequeraPagoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.OffsetDateTime;
import java.util.List;

@FeignClient(name = "tesoreria-core-service/api/tesoreria/core/chequeraPago")
public interface ChequeraPagoClient {

    @GetMapping("/pendientesFactura/{fechaPago}")
    List<ChequeraPagoDto> pendientesFactura(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime fechaPago);

    @GetMapping("{chequeraPagoId}")
    ChequeraPagoDto findByChequeraPagoId(@PathVariable Long chequeraPagoId);

}
