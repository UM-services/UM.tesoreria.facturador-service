package um.tesoreria.facturador.hexagonal.facturacion.datos.comprobante.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Comprobante {

    private Integer comprobanteId;
    private String descripcion;
    @Builder.Default
    private Byte facturacionElectronica = 0;
    private Integer comprobanteAfipId;
    private Integer puntoVenta;

}
