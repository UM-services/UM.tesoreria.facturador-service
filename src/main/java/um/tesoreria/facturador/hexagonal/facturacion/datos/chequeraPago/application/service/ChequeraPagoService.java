package um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.domain.model.ChequeraPago;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.domain.ports.in.GetChequeraPagoUseCase;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.domain.ports.in.GetPendientesFacturaUseCase;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChequeraPagoService {

    private final GetPendientesFacturaUseCase getPendientesFacturaUseCase;
    private final GetChequeraPagoUseCase getChequeraPagoUseCase;

    public List<ChequeraPago> getPendientes(OffsetDateTime fechaPago) {
        return getPendientesFacturaUseCase.getPendientes(fechaPago);
    }

    public ChequeraPago getById(Long chequeraPagoId) {
        return getChequeraPagoUseCase.getById(chequeraPagoId);
    }

}
