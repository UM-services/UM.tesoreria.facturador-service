package um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FacturacionElectronica {

    private Long facturacionElectronicaId;
    private Long chequeraPagoId;
    private Integer comprobanteId;

    @Builder.Default
    private Long numeroComprobante = 0L;
    private BigDecimal personaId;
    private String tipoDocumento;
    private String apellido;
    private String nombre;
    private String cuit;

    @Builder.Default
    private String condicionIva = "";

    @Builder.Default
    private BigDecimal importe = BigDecimal.ZERO;
    private String cae;
    private OffsetDateTime fechaRecibo;
    private OffsetDateTime fechaVencimientoCae;

    @Builder.Default
    private Byte enviada = 0;

    @Builder.Default
    private Integer retries = 0;

}
