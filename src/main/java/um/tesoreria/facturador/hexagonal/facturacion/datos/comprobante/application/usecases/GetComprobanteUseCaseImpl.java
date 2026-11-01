package um.tesoreria.facturador.hexagonal.facturacion.datos.comprobante.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import um.tesoreria.facturador.hexagonal.facturacion.datos.comprobante.application.exception.ComprobanteException;
import um.tesoreria.facturador.hexagonal.facturacion.datos.comprobante.domain.model.Comprobante;
import um.tesoreria.facturador.hexagonal.facturacion.datos.comprobante.domain.ports.in.GetComprobanteUseCase;
import um.tesoreria.facturador.hexagonal.facturacion.datos.comprobante.domain.ports.out.ComprobanteRepository;

@Component
@RequiredArgsConstructor
public class GetComprobanteUseCaseImpl implements GetComprobanteUseCase {

    private final ComprobanteRepository comprobanteRepository;

    @Override
    public Comprobante getById(Integer comprobanteId) {
        return comprobanteRepository.findByComprobanteId(comprobanteId)
                .orElseThrow(() -> new ComprobanteException(comprobanteId));
    }

}
