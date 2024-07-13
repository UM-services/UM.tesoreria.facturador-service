package um.tesoreria.facturador.client;

import um.tesoreria.facturador.kotlin.model.dto.ChequeraFacturacionElectronicaDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import um.tesoreria.facturador.kotlin.model.dto.ChequeraFacturacionElectronicaDto;

@FeignClient(name = "core-service/chequeraFacturacionElectronica")
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
