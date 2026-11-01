package um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.domain.model.ChequeraPago;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.domain.ports.in.GetPendientesFacturaUseCase;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.domain.ports.out.ChequeraPagoRepository;

import java.time.OffsetDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GetPendientesFacturaUseCaseImpl implements GetPendientesFacturaUseCase {

    private final ChequeraPagoRepository chequeraPagoRepository;

    @Override
    public List<ChequeraPago> getPendientes(OffsetDateTime fechaPago) {
        return chequeraPagoRepository.findPendientesFactura(fechaPago);
    }

}
