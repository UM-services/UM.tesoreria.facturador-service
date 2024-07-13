package um.tesoreria.facturador.controller;

import um.tesoreria.facturador.client.ChequeraPagoClient;
import um.tesoreria.facturador.client.FacturaSenderClient;
import um.tesoreria.facturador.kotlin.model.dto.ChequeraPagoDto;
import um.tesoreria.facturador.kotlin.model.dto.FacturacionDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/facturador")
public class FacturadorController {

    private final FacturaSenderClient facturaSenderClient;

    private final ChequeraPagoClient chequeraPagoClient;

    @Autowired
    public FacturadorController(FacturaSenderClient facturaSenderClient, ChequeraPagoClient chequeraPagoClient) {
        this.facturaSenderClient = facturaSenderClient;
        this.chequeraPagoClient = chequeraPagoClient;
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

    @GetMapping("/pendientesFactura/{fechaPago}")
    public ResponseEntity<List<ChequeraPagoDto>> pendientesFactura(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime fechaPago) {
        return new ResponseEntity<>(chequeraPagoClient.pendientesFactura(fechaPago), HttpStatus.OK);
    }

}
