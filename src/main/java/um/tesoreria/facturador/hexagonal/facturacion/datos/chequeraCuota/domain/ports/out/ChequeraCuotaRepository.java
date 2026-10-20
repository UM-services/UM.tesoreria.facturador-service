package um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraCuota.domain.ports.out;

import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraCuota.domain.model.ChequeraCuota;

import java.util.Optional;

public interface ChequeraCuotaRepository {

    Optional<ChequeraCuota> findByUnique(Integer facultadId,
                                         Integer tipoChequeraId,
                                         Long chequeraSerieId,
                                         Integer productoId,
                                         Integer alternativaId,
                                         Integer cuotaId);

}
