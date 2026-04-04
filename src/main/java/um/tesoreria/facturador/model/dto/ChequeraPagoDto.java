package um.tesoreria.facturador.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import um.tesoreria.facturador.tool.Jsonifier;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChequeraPagoDto {

    private Long chequeraPagoId;
    private Long chequeraCuotaId;
    private Integer facultadId;
    private Integer tipoChequeraId;
    private Long chequeraSerieId;
    private Integer productoId;
    private Integer alternativaId;
    private Integer cuotaId;
    private Integer orden;
    private Integer mes = 0;
    private Integer anho = 0;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    private OffsetDateTime fecha;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    private OffsetDateTime acreditacion;

    private BigDecimal importe = BigDecimal.ZERO;
    private String path = "";
    private String archivo = "";
    private String observaciones = "";
    private Long archivoBancoId;
    private Long archivoBancoIdAcreditacion;
    private Integer verificador = 0;
    private Integer tipoPagoId;
    private TipoPagoDto tipoPago;
    private ProductoDto producto;
    private ChequeraCuotaDto chequeraCuota;

    public String jsonify() {
        return Jsonifier.builder(this).build();
    }

}
