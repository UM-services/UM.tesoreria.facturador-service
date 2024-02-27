package ar.edu.um.tesoreria.facturador.controller;

import ar.edu.um.tesoreria.facturador.client.FacturaSenderClient;
import ar.edu.um.tesoreria.facturador.kotlin.model.dto.FacturacionDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/facturador")
public class FacturadorController {

    private final FacturaSenderClient facturaSenderClient;

    @Autowired
    public FacturadorController(FacturaSenderClient facturaSenderClient) {
        this.facturaSenderClient = facturaSenderClient;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello Facturador");
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
