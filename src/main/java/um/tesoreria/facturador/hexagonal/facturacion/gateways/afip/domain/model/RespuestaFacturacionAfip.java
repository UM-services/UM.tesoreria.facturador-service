package um.tesoreria.facturador.hexagonal.facturacion.gateways.afip.domain.model;

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
public class RespuestaFacturacionAfip {

    @Builder.Default
    private String resultado = "";
    @Builder.Default
    private String cae = "";
    @Builder.Default
    private String vencimientoCae = "";
    @Builder.Default
    private Long numeroComprobante = 0L;

}
