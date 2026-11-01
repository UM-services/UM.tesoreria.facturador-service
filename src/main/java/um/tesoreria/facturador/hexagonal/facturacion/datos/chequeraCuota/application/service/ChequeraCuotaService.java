package um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraCuota.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraCuota.domain.model.ChequeraCuota;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraCuota.domain.ports.in.GetChequeraCuotaByUniqueUseCase;

@Service
@RequiredArgsConstructor
public class ChequeraCuotaService {

    private final GetChequeraCuotaByUniqueUseCase getChequeraCuotaByUniqueUseCase;

    public ChequeraCuota getByUnique(Integer facultadId,
                                     Integer tipoChequeraId,
                                     Long chequeraSerieId,
                                     Integer productoId,
                                     Integer alternativaId,
                                     Integer cuotaId) {
        return getChequeraCuotaByUniqueUseCase.getByUnique(facultadId, tipoChequeraId, chequeraSerieId,
                productoId, alternativaId, cuotaId);
    }

}
