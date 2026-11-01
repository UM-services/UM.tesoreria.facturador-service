package um.tesoreria.facturador.hexagonal.facturacion.procesos.emision.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import um.tesoreria.facturador.hexagonal.facturacion.procesos.emision.application.service.EmisionService;

@RestController
@RequestMapping("/api/tesoreria/facturador")
@RequiredArgsConstructor
public class EmisionController {

    private final EmisionService emisionService;

    @GetMapping("/facturaPendientes")
    public ResponseEntity<String> facturaPendientes() {
        return ResponseEntity.ok(emisionService.facturaPendientes());
    }

    @GetMapping("/facturaOne/{chequeraPagoId}")
    public ResponseEntity<String> facturaOne(@PathVariable Long chequeraPagoId) {
        return ResponseEntity.ok(emisionService.facturaOne(chequeraPagoId));
    }

}
