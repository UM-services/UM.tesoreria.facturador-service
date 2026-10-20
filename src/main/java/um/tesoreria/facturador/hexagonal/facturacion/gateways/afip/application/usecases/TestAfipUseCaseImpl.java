package um.tesoreria.facturador.hexagonal.facturacion.gateways.afip.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import um.tesoreria.facturador.hexagonal.facturacion.gateways.afip.domain.ports.in.TestAfipUseCase;
import um.tesoreria.facturador.hexagonal.facturacion.gateways.afip.domain.ports.out.FacturacionAfipRepository;

@Component
@RequiredArgsConstructor
public class TestAfipUseCaseImpl implements TestAfipUseCase {

    private final FacturacionAfipRepository facturacionAfipRepository;

    @Override
    public String test() {
        return facturacionAfipRepository.test();
    }

}
