package um.tesoreria.facturador.hexagonal.facturacion.gateways.recibo.infrastructure.client.adapter;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import um.tesoreria.facturador.hexagonal.facturacion.gateways.recibo.application.exception.EnvioReciboException;
import um.tesoreria.facturador.hexagonal.facturacion.gateways.recibo.domain.ports.out.ReciboRepository;
import um.tesoreria.facturador.hexagonal.facturacion.gateways.recibo.infrastructure.client.ReciboClient;

@Component
@RequiredArgsConstructor
public class ReciboClientAdapter implements ReciboRepository {

    private final ReciboClient reciboClient;

    @Override
    public String enviar(Long facturacionElectronicaId) {
        try {
            return reciboClient.send(facturacionElectronicaId);
        } catch (FeignException.BadRequest e) {
            throw new EnvioReciboException(facturacionElectronicaId, e);
        }
    }

}
