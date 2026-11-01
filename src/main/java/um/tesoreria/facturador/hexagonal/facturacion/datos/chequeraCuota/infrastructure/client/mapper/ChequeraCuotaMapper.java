package um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraCuota.infrastructure.client.mapper;

import org.springframework.stereotype.Component;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraCuota.domain.model.ChequeraCuota;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraCuota.infrastructure.client.dto.ChequeraCuotaResponse;

@Component
public class ChequeraCuotaMapper {

    public ChequeraCuota toDomain(ChequeraCuotaResponse response) {
        if (response == null) return null;

        var serie = response.getChequeraSerie();
        var persona = serie == null ? null : serie.getPersona();

        return ChequeraCuota.builder()
                .chequeraId(response.getChequeraId() != null
                        ? response.getChequeraId()
                        : (serie == null ? null : serie.getChequeraId()))
                .personaId(persona == null ? null : persona.getPersonaId())
                .personaApellido(persona == null ? null : persona.getApellido())
                .personaNombre(persona == null ? null : persona.getNombre())
                .build();
    }

}
