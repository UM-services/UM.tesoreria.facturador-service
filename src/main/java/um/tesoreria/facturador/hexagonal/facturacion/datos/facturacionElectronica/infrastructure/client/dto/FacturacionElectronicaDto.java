package um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.infrastructure.client.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import um.tesoreria.facturador.hexagonal.facturacion.infrastructure.client.support.Jsonifyable;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FacturacionElectronicaDto implements Jsonifyable {

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaRecibo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaVencimientoCae;

    @Builder.Default
    private Byte enviada = 0;

    @Builder.Default
    private Integer retries = 0;

    /*
     * Estos dos campos viajan siempre como null en el alta (contrato heredado) y no se
     * consumen en este servicio. Se declaran como Object para preservar el payload.
     */
    private Object chequeraPago;
    private Object comprobante;

}
