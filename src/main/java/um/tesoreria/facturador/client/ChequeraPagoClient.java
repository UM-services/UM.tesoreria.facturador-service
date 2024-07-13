package um.tesoreria.facturador.client;

import um.tesoreria.facturador.kotlin.model.dto.ChequeraPagoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import um.tesoreria.facturador.kotlin.model.dto.ChequeraPagoDto;

import java.time.OffsetDateTime;
import java.util.List;

@FeignClient(name = "core-service/chequerapago")
public interface ChequeraPagoClient {

    @GetMapping("/pendientesFactura/{fechaPago}")
    List<ChequeraPagoDto> pendientesFactura(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime fechaPago);

}
