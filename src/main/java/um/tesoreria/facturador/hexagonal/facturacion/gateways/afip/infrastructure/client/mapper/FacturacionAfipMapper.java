package um.tesoreria.facturador.hexagonal.facturacion.gateways.afip.infrastructure.client.mapper;

import org.springframework.stereotype.Component;
import um.tesoreria.facturador.hexagonal.facturacion.gateways.afip.domain.model.RespuestaFacturacionAfip;
import um.tesoreria.facturador.hexagonal.facturacion.gateways.afip.domain.model.SolicitudFacturacionAfip;
import um.tesoreria.facturador.hexagonal.facturacion.gateways.afip.infrastructure.client.dto.FacturacionAfipDto;

@Component
public class FacturacionAfipMapper {

    public FacturacionAfipDto toDto(SolicitudFacturacionAfip solicitud) {
        if (solicitud == null) return null;

        var builder = FacturacionAfipDto.builder()
                .tipoDocumento(solicitud.getTipoDocumento())
                .documento(solicitud.getDocumento())
                .tipoAfip(solicitud.getTipoAfip())
                .puntoVenta(solicitud.getPuntoVenta())
                .idCondicionIva(solicitud.getIdCondicionIva());

        if (solicitud.getTotal() != null) builder.total(solicitud.getTotal());
        if (solicitud.getNeto() != null) builder.neto(solicitud.getNeto());

        return builder.build();
    }

    public RespuestaFacturacionAfip toDomain(FacturacionAfipDto dto) {
        if (dto == null) return null;

        var builder = RespuestaFacturacionAfip.builder();
        if (dto.getResultado() != null) builder.resultado(dto.getResultado());
        if (dto.getCae() != null) builder.cae(dto.getCae());
        if (dto.getVencimientoCae() != null) builder.vencimientoCae(dto.getVencimientoCae());
        if (dto.getNumeroComprobante() != null) builder.numeroComprobante(dto.getNumeroComprobante());

        return builder.build();
    }

}
