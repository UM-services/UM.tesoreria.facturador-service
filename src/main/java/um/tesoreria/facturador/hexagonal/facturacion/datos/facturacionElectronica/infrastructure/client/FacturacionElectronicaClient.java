package um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.infrastructure.client.dto.FacturacionElectronicaDto;

import java.util.List;

@FeignClient(name = "tesoreria-core-service/api/tesoreria/core/facturacionElectronica")
public interface FacturacionElectronicaClient {

    @GetMapping("/chequera/{facultadId}/{tipoChequeraId}/{chequeraSerieId}")
    List<FacturacionElectronicaDto> findAllByChequera(@PathVariable Integer facultadId,
                                                      @PathVariable Integer tipoChequeraId,
                                                      @PathVariable Long chequeraSerieId);

    @GetMapping("/pendientes")
    List<FacturacionElectronicaDto> find3Pendientes();

    @GetMapping("/{facturacionElectronicaId}")
    FacturacionElectronicaDto findByFacturacionElectronicaId(@PathVariable Long facturacionElectronicaId);

    @GetMapping("/pago/{chequeraPagoId}")
    FacturacionElectronicaDto findByChequeraPagoId(@PathVariable Long chequeraPagoId);

    @PostMapping("/")
    FacturacionElectronicaDto add(@RequestBody FacturacionElectronicaDto facturacionElectronica);

}
