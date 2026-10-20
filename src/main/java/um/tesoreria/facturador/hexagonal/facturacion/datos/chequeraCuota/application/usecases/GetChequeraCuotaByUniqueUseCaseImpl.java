package um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraCuota.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraCuota.application.exception.ChequeraCuotaException;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraCuota.domain.model.ChequeraCuota;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraCuota.domain.ports.in.GetChequeraCuotaByUniqueUseCase;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraCuota.domain.ports.out.ChequeraCuotaRepository;

@Component
@RequiredArgsConstructor
public class GetChequeraCuotaByUniqueUseCaseImpl implements GetChequeraCuotaByUniqueUseCase {

    private final ChequeraCuotaRepository chequeraCuotaRepository;

    @Override
    public ChequeraCuota getByUnique(Integer facultadId,
                                     Integer tipoChequeraId,
                                     Long chequeraSerieId,
                                     Integer productoId,
                                     Integer alternativaId,
                                     Integer cuotaId) {
        return chequeraCuotaRepository.findByUnique(facultadId, tipoChequeraId, chequeraSerieId,
                        productoId, alternativaId, cuotaId)
                .orElseThrow(() -> new ChequeraCuotaException(chequeraSerieId, cuotaId));
    }

}
