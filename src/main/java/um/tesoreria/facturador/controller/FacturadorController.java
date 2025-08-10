package um.tesoreria.facturador.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import um.tesoreria.facturador.service.FacturadorService;

@RestController
@RequestMapping("/api/tesoreria/facturador")
public class FacturadorController {

    private final FacturadorService service;

    public FacturadorController(FacturadorService service) {
        this.service = service;
    }

    @GetMapping("/facturaPendientes")
    public ResponseEntity<String> facturaPendientes() {
        return ResponseEntity.ok(service.facturaPendientes());
    }

    @GetMapping("/facturaOne/{chequeraPagoId}")
    public ResponseEntity<String> facturaOne(@PathVariable Long chequeraPagoId) {
        return ResponseEntity.ok(service.facturaOne(chequeraPagoId));
    }

    @GetMapping("/sendOne/pago/{chequeraPagoId}")
    public ResponseEntity<String> sendOneByChequeraPagoId(@PathVariable Long chequeraPagoId) {
        return ResponseEntity.ok(service.sendOneByChequeraPagoId(chequeraPagoId));
    }

    @GetMapping("/sendOne/recibo/{facturacionElectronicaId}")
    public ResponseEntity<String> sendOneByFacturacionElectronicaId(@PathVariable Long facturacionElectronicaId) {
        return ResponseEntity.ok(service.sendOneByFacturacionElectronicaId(facturacionElectronicaId));
    }

}
