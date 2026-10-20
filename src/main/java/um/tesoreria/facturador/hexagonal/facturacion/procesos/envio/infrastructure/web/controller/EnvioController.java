package um.tesoreria.facturador.hexagonal.facturacion.procesos.envio.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import um.tesoreria.facturador.hexagonal.facturacion.procesos.envio.application.service.EnvioService;

@RestController
@RequestMapping("/api/tesoreria/facturador")
@RequiredArgsConstructor
public class EnvioController {

    private final EnvioService envioService;

    @GetMapping("/sendOne/pago/{chequeraPagoId}")
    public ResponseEntity<String> sendOneByChequeraPagoId(@PathVariable Long chequeraPagoId) {
        return ResponseEntity.ok(envioService.sendOneByChequeraPagoId(chequeraPagoId));
    }

    @GetMapping("/sendOne/recibo/{facturacionElectronicaId}")
    public ResponseEntity<String> sendOneByFacturacionElectronicaId(@PathVariable Long facturacionElectronicaId) {
        return ResponseEntity.ok(envioService.sendOneByFacturacionElectronicaId(facturacionElectronicaId));
    }

}
