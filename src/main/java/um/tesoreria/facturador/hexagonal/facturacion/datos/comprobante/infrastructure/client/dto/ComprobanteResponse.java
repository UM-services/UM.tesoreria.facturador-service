package um.tesoreria.facturador.hexagonal.facturacion.datos.comprobante.infrastructure.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ComprobanteResponse {

    private Integer comprobanteId;
    private String descripcion;
    private Byte facturacionElectronica;
    private Integer comprobanteAfipId;
    private Integer puntoVenta;

}
