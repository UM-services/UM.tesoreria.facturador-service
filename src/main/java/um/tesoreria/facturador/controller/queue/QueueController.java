package um.tesoreria.facturador.controller.queue;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import um.tesoreria.facturador.service.queue.QueueService;

@RestController
@RequestMapping("/api/tesoreria/facturador/tester")
public class QueueController {

    private final QueueService service;

    public QueueController(QueueService service) {
        this.service = service;
    }

    @GetMapping("/test/{message}")
    public ResponseEntity<Void> test(@PathVariable String message) {
        service.test(message);
        return ResponseEntity.ok().build();
    }

}
