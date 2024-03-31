package ar.edu.um.tesoreria.facturador.client;

import ar.edu.um.tesoreria.facturador.kotlin.model.dto.ChequeraPagoDto;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.OffsetDateTime;
import java.util.List;

@Component
public class ChequeraPagoClient {

    public List<ChequeraPagoDto> pendientesFactura(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime fechaPago) {
        String baseUrl = "http://192.168.201.211:8092/chequerapago";
        WebClient webClient = WebClient.builder().baseUrl(baseUrl).build();
        Flux<ChequeraPagoDto> response = webClient.get().uri("/pendientesFactura/" + fechaPago).retrieve()
                .bodyToFlux(ChequeraPagoDto.class);
        return response.collectList().block();
    }

}
