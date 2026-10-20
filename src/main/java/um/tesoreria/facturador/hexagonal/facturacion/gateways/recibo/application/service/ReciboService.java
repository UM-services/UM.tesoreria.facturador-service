package um.tesoreria.facturador.hexagonal.facturacion.gateways.recibo.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import um.tesoreria.facturador.hexagonal.facturacion.gateways.recibo.domain.ports.in.EnviarReciboUseCase;

@Service
@RequiredArgsConstructor
public class ReciboService {

    private final EnviarReciboUseCase enviarReciboUseCase;

    public String enviar(Long facturacionElectronicaId) {
        return enviarReciboUseCase.enviar(facturacionElectronicaId);
    }

}
