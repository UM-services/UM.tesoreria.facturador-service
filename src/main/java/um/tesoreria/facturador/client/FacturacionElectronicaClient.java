package um.tesoreria.facturador.client;

import um.tesoreria.facturador.kotlin.model.dto.FacturacionElectronicaDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import um.tesoreria.facturador.kotlin.model.dto.FacturacionElectronicaDto;

import java.util.List;

@FeignClient(name = "core-service/facturacionElectronica")
public interface FacturacionElectronicaClient {

    @GetMapping("/chequera/{facultadId}/{tipoChequeraId}/{chequeraSerieId}")
    List<FacturacionElectronicaDto> findAllByChequera(@PathVariable Integer facultadId, @PathVariable Integer tipoChequeraId, @PathVariable Long chequeraSerieId) ;

    @GetMapping("/{facturacionElectronicaId}")
    FacturacionElectronicaDto findByFacturacionElectronicaId(@PathVariable Long facturacionElectronicaId) ;

    @PostMapping("/")
    FacturacionElectronicaDto add(@RequestBody FacturacionElectronicaDto facturacionElectronica) ;

}
