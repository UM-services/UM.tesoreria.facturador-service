package um.tesoreria.facturador.hexagonal.facturacion.datos.comprobante.infrastructure.client.mapper;

import org.springframework.stereotype.Component;
import um.tesoreria.facturador.hexagonal.facturacion.datos.comprobante.domain.model.Comprobante;
import um.tesoreria.facturador.hexagonal.facturacion.datos.comprobante.infrastructure.client.dto.ComprobanteResponse;

@Component
public class ComprobanteMapper {

    public Comprobante toDomain(ComprobanteResponse response) {
        if (response == null) return null;

        var builder = Comprobante.builder()
                .comprobanteId(response.getComprobanteId())
                .descripcion(response.getDescripcion())
                .comprobanteAfipId(response.getComprobanteAfipId())
                .puntoVenta(response.getPuntoVenta());

        if (response.getFacturacionElectronica() != null) {
            builder.facturacionElectronica(response.getFacturacionElectronica());
        }

        return builder.build();
    }

}
