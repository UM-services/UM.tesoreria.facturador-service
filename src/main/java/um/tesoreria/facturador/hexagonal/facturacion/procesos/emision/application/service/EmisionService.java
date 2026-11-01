package um.tesoreria.facturador.hexagonal.facturacion.procesos.emision.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import um.tesoreria.facturador.hexagonal.facturacion.procesos.emision.domain.ports.in.FacturarPagoUseCase;
import um.tesoreria.facturador.hexagonal.facturacion.procesos.emision.domain.ports.in.FacturarPendientesUseCase;

@Service
@RequiredArgsConstructor
public class EmisionService {

    private final FacturarPendientesUseCase facturarPendientesUseCase;
    private final FacturarPagoUseCase facturarPagoUseCase;

    public String facturaPendientes() {
        return facturarPendientesUseCase.facturarPendientes();
    }

    public String facturaOne(Long chequeraPagoId) {
        return facturarPagoUseCase.facturarPago(chequeraPagoId);
    }

}
