package um.tesoreria.facturador.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import um.tesoreria.facturador.service.FacturadorService;

@RestController
@RequestMapping("/api/tesoreria/facturador")
public class FacturadorController {

    private final FacturadorService service;

    @Autowired
    public FacturadorController(FacturadorService service) {
        this.service = service;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello Facturador");
    }

    @GetMapping("/facturaPendientes")
    public ResponseEntity<String> facturaPendientes() {
        return new ResponseEntity<>(service.facturaPendientes(), HttpStatus.OK);
    }

    @Scheduled(cron = "0 0 2 * * *")
    public void facturaPendientesScheduled() {
        new ResponseEntity<>(service.facturaPendientes(), HttpStatus.OK);
    }

    @GetMapping("/facturaOne/{chequeraPagoId}")
    public ResponseEntity<String> facturaOne(@PathVariable Long chequeraPagoId) {
        return new ResponseEntity<>(service.facturaOne(chequeraPagoId), HttpStatus.OK);
    }

    @GetMapping("/sendOne/pago/{chequeraPagoId}")
    public ResponseEntity<String> sendOneByChequeraPagoId(@PathVariable Long chequeraPagoId) {
        return new ResponseEntity<>(service.sendOneByChequeraPagoId(chequeraPagoId), HttpStatus.OK);
    }

    @GetMapping("/sendOne/recibo/{facturacionElectronicaId}")
    public ResponseEntity<String> sendOneByFacturacionElectronicaId(@PathVariable Long facturacionElectronicaId) {
        return new ResponseEntity<>(service.sendOneByFacturacionElectronicaId(facturacionElectronicaId), HttpStatus.OK);
    }

    @GetMapping("/testInvoiceQueue/{facturaElectronicaId}")
    public ResponseEntity<Void> testInvoiceQueue(@PathVariable Long facturaElectronicaId) {
        service.testInvoiceQueue(facturaElectronicaId);
        return ResponseEntity.ok().build();
    }

}
