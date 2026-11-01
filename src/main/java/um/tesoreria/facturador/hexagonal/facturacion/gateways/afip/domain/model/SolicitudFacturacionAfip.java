package um.tesoreria.facturador.hexagonal.facturacion.gateways.afip.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SolicitudFacturacionAfip {

    private Integer tipoDocumento;
    private String documento;
    private Integer tipoAfip;
    private Integer puntoVenta;
    private BigDecimal total;
    private BigDecimal neto;
    private Integer idCondicionIva;

}
