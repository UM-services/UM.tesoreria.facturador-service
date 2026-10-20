package um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.infrastructure.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChequeraPagoResponse {

    private Long chequeraPagoId;
    private Integer facultadId;
    private Integer tipoChequeraId;
    private Long chequeraSerieId;
    private Integer productoId;
    private Integer alternativaId;
    private Integer cuotaId;
    private BigDecimal importe;
    private ChequeraCuotaResponse chequeraCuota;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ChequeraCuotaResponse {
        private Long chequeraId;
        private ChequeraSerieResponse chequeraSerie;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ChequeraSerieResponse {
        private Long chequeraId;
        private PersonaResponse persona;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PersonaResponse {
        private BigDecimal personaId;
        private String apellido;
        private String nombre;
    }

}
