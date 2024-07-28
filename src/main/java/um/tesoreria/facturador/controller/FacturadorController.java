package um.tesoreria.facturador.controller;

import org.springframework.scheduling.annotation.Scheduled;
import um.tesoreria.facturador.client.tesoreria.FacturaSenderClient;
import um.tesoreria.facturador.kotlin.tesoreria.afip.dto.FacturacionDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import um.tesoreria.facturador.service.FacturadorService;

@RestController
@RequestMapping("/api/tesoreria/facturador")
public class FacturadorController {

    private final FacturadorService service;
    private final FacturaSenderClient facturaSenderClient;

    @Autowired
    public FacturadorController(FacturadorService service, FacturaSenderClient facturaSenderClient) {
        this.service = service;
        this.facturaSenderClient = facturaSenderClient;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello Facturador");
    }

    @Scheduled(cron = "0 0 1 * * *")
    @GetMapping("/facturaPendientes")
    public ResponseEntity<String> facturaPendientes() {
        return new ResponseEntity<>(service.facturaPendientes(), HttpStatus.OK);
    }

    @GetMapping("/facturaOne/{chequeraPagoId}")
    public ResponseEntity<String> facturaOne(@PathVariable Long chequeraPagoId) {
        return new ResponseEntity<>(service.facturaOne(chequeraPagoId), HttpStatus.OK);
    }

    @CircuitBreaker(name = "facturaSenderCircuitBreaker", fallbackMethod = "fallbackSend")
    @PostMapping("/send")
    public ResponseEntity<FacturacionDto> send(@RequestBody FacturacionDto facturacionDto) {
        return ResponseEntity.ok(facturaSenderClient.send(facturacionDto));
    }

    private ResponseEntity<FacturacionDto> fallbackSend(@RequestBody FacturacionDto facturacionDto, RuntimeException exception) {
        return new ResponseEntity("Sender no disponible", HttpStatus.OK);
    }

}
