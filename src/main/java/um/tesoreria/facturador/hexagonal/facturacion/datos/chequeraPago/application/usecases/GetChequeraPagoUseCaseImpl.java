package um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.application.exception.ChequeraPagoException;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.domain.model.ChequeraPago;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.domain.ports.in.GetChequeraPagoUseCase;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.domain.ports.out.ChequeraPagoRepository;

@Component
@RequiredArgsConstructor
public class GetChequeraPagoUseCaseImpl implements GetChequeraPagoUseCase {

    private final ChequeraPagoRepository chequeraPagoRepository;

    @Override
    public ChequeraPago getById(Long chequeraPagoId) {
        return chequeraPagoRepository.findByChequeraPagoId(chequeraPagoId)
                .orElseThrow(() -> new ChequeraPagoException(chequeraPagoId));
    }

}
