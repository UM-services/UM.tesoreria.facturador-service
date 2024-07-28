package um.tesoreria.facturador.client.tesoreria.core;

import um.tesoreria.facturador.kotlin.tesoreria.core.dto.ChequeraFacturacionElectronicaDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "tesoreria-core-service/api/tesoreria/core/chequeraFacturacionElectronica")
public interface ChequeraFacturacionElectronicaClient {

    @GetMapping("/chequera/{chequeraId}")
    ChequeraFacturacionElectronicaDto findByChequeraId(@PathVariable Long chequeraId);

    @PostMapping("/")
    ChequeraFacturacionElectronicaDto add(@RequestBody ChequeraFacturacionElectronicaDto chequeraFacturacionElectronica);

    @PutMapping("/{chequeraFacturacionElectronicaId}")
    ChequeraFacturacionElectronicaDto update(@RequestBody ChequeraFacturacionElectronicaDto chequeraFacturacionElectronica, @PathVariable Long chequeraFacturacionElectronicaId);

    @DeleteMapping("/{chequeraFacturacionElectronicaId}")
    Void deleteByChequeraFacturacionElectronicaId(@PathVariable Long chequeraFacturacionElectronicaId);

}
