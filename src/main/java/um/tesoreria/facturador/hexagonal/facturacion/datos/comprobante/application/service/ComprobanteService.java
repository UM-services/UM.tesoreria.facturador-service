package um.tesoreria.facturador.hexagonal.facturacion.datos.comprobante.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import um.tesoreria.facturador.hexagonal.facturacion.datos.comprobante.domain.model.Comprobante;
import um.tesoreria.facturador.hexagonal.facturacion.datos.comprobante.domain.ports.in.GetComprobanteUseCase;

@Service
@RequiredArgsConstructor
public class ComprobanteService {

    private final GetComprobanteUseCase getComprobanteUseCase;

    public Comprobante getById(Integer comprobanteId) {
        return getComprobanteUseCase.getById(comprobanteId);
    }

}
