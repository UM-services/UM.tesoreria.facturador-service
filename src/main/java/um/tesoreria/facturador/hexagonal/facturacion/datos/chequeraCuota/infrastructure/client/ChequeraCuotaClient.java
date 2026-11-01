package um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraCuota.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraCuota.infrastructure.client.dto.ChequeraCuotaResponse;

import java.time.OffsetDateTime;
import java.util.List;

@FeignClient(name = "tesoreria-core-service/api/tesoreria/core/chequeraCuota")
public interface ChequeraCuotaClient {

    @GetMapping("/chequera/{facultadId}/{tipoChequeraId}/{chequeraSerieId}/{alternativaId}")
    List<ChequeraCuotaResponse> findAllByChequera(@PathVariable Integer facultadId,
                                                  @PathVariable Integer tipoChequeraId,
                                                  @PathVariable Long chequeraSerieId,
                                                  @PathVariable Integer alternativaId);

    @GetMapping("/inconsistencias/{desde}/{hasta}")
    List<ChequeraCuotaResponse> findAllInconsistencias(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime desde,
                                                       @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime hasta);

    @GetMapping("/{chequeraCuotaId}")
    ChequeraCuotaResponse findByChequeraCuotaId(@PathVariable Long chequeraCuotaId);

    @GetMapping("/unique/{facultadId}/{tipoChequeraId}/{chequeraSerieId}/{productoId}/{alternativaId}/{cuotaId}")
    ChequeraCuotaResponse findByUnique(@PathVariable Integer facultadId,
                                       @PathVariable Integer tipoChequeraId,
                                       @PathVariable Long chequeraSerieId,
                                       @PathVariable Integer productoId,
                                       @PathVariable Integer alternativaId,
                                       @PathVariable Integer cuotaId);

}
