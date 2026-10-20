package um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.infrastructure.client.dto.ChequeraPagoResponse;

import java.time.OffsetDateTime;
import java.util.List;

@FeignClient(name = "tesoreria-core-service/api/tesoreria/core/chequeraPago")
public interface ChequeraPagoClient {

    @GetMapping("/pendientesFactura/{fechaPago}")
    List<ChequeraPagoResponse> pendientesFactura(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime fechaPago);

    @GetMapping("{chequeraPagoId}")
    ChequeraPagoResponse findByChequeraPagoId(@PathVariable Long chequeraPagoId);

}
