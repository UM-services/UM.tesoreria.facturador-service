package um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraCuota.domain.ports.in;

import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraCuota.domain.model.ChequeraCuota;

public interface GetChequeraCuotaByUniqueUseCase {

    ChequeraCuota getByUnique(Integer facultadId,
                              Integer tipoChequeraId,
                              Long chequeraSerieId,
                              Integer productoId,
                              Integer alternativaId,
                              Integer cuotaId);

}
