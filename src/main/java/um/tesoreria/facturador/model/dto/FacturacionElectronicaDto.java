package um.tesoreria.facturador.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import um.tesoreria.facturador.tool.Jsonifier;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacturacionElectronicaDto {

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    private OffsetDateTime fechaRecibo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    private OffsetDateTime fechaVencimientoCae;

    @Builder.Default
    private Byte enviada = 0;

    @Builder.Default
    private Integer retries = 0;
    private ChequeraPagoDto chequeraPago;
    private ComprobanteDto comprobante;

    public String jsonify() {
        return Jsonifier.builder(this).build();
    }

}
