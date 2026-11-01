package um.tesoreria.facturador.hexagonal.facturacion.gateways.recibo.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import um.tesoreria.facturador.hexagonal.facturacion.gateways.recibo.domain.ports.in.EnviarReciboUseCase;
import um.tesoreria.facturador.hexagonal.facturacion.gateways.recibo.domain.ports.out.ReciboRepository;

@Component
@RequiredArgsConstructor
public class EnviarReciboUseCaseImpl implements EnviarReciboUseCase {

    private final ReciboRepository reciboRepository;

    @Override
    public String enviar(Long facturacionElectronicaId) {
        return reciboRepository.enviar(facturacionElectronicaId);
    }

}
