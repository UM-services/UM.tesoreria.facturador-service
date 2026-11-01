package um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraFacturacionElectronica.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraFacturacionElectronica.infrastructure.client.dto.ChequeraFacturacionElectronicaResponse;

@FeignClient(name = "tesoreria-core-service/api/tesoreria/core/chequeraFacturacionElectronica")
public interface ChequeraFacturacionElectronicaClient {

    @GetMapping("/chequera/{chequeraId}")
    ChequeraFacturacionElectronicaResponse findByChequeraId(@PathVariable Long chequeraId);

    @PostMapping("/")
    ChequeraFacturacionElectronicaResponse add(@RequestBody ChequeraFacturacionElectronicaResponse chequeraFacturacionElectronica);

    @PutMapping("/{chequeraFacturacionElectronicaId}")
    ChequeraFacturacionElectronicaResponse update(@RequestBody ChequeraFacturacionElectronicaResponse chequeraFacturacionElectronica,
                                                  @PathVariable Long chequeraFacturacionElectronicaId);

    @DeleteMapping("/{chequeraFacturacionElectronicaId}")
    Void deleteByChequeraFacturacionElectronicaId(@PathVariable Long chequeraFacturacionElectronicaId);

}
