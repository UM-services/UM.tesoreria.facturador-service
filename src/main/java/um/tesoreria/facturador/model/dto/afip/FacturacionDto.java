package um.tesoreria.facturador.model.dto.afip;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import um.tesoreria.facturador.tool.Jsonifier;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacturacionDto {

    @Builder.Default
    @JsonProperty(value = "tipo_documento")
    private Integer tipoDocumento = 0;

    @Builder.Default
    private String documento = "0";

    @Builder.Default
    @JsonProperty(value = "tipo_afip")
    private Integer tipoAfip = 0;

    @Builder.Default
    @JsonProperty(value = "punto_venta")
    private Integer puntoVenta = 0;

    @Builder.Default
    private BigDecimal total = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal exento = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal neto = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal neto105 = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal iva = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal iva105 = BigDecimal.ZERO;
    @Builder.Default
    private String resultado = "";
    @Builder.Default
    private String cae = "";

    @Builder.Default
    @JsonProperty(value = "vencimiento_cae")
    private String vencimientoCae = "";

    @Builder.Default
    @JsonProperty(value = "numero_comprobante")
    private Long numeroComprobante = 0L;

    @JsonProperty(value = "asociado_tipo_afip")
    private Integer asociadoTipoAfip;

    @JsonProperty(value = "asociado_punto_venta")
    private Integer asociadoPuntoVenta;

    @JsonProperty(value = "asociado_numero_comprobante")
    private Long asociadoNumeroComprobante;

    @JsonProperty(value = "asociado_fecha_comprobante")
    private String asociadoFechaComprobante;

    @JsonProperty(value = "id_condicion_iva")
    private Integer idCondicionIva;

    public String jsonify() {
        return Jsonifier.builder(this).build();
    }

}
