package um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.infrastructure.client.mapper;

import org.springframework.stereotype.Component;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.domain.model.ChequeraPago;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.infrastructure.client.dto.ChequeraPagoResponse;

@Component
public class ChequeraPagoMapper {

    public ChequeraPago toDomain(ChequeraPagoResponse response) {
        if (response == null) return null;

        var cuota = response.getChequeraCuota();
        var serie = cuota == null ? null : cuota.getChequeraSerie();
        var persona = serie == null ? null : serie.getPersona();

        var builder = ChequeraPago.builder()
                .chequeraPagoId(response.getChequeraPagoId())
                .facultadId(response.getFacultadId())
                .tipoChequeraId(response.getTipoChequeraId())
                .chequeraSerieId(response.getChequeraSerieId())
                .productoId(response.getProductoId())
                .alternativaId(response.getAlternativaId())
                .cuotaId(response.getCuotaId())
                .tieneCuota(cuota != null)
                .chequeraId(serie == null ? null : serie.getChequeraId())
                .personaId(persona == null ? null : persona.getPersonaId())
                .personaApellido(persona == null ? null : persona.getApellido())
                .personaNombre(persona == null ? null : persona.getNombre());

        if (response.getImporte() != null) {
            builder.importe(response.getImporte());
        }

        return builder.build();
    }

}
