package um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraFacturacionElectronica.infrastructure.client.dto;

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
public class ChequeraFacturacionElectronicaResponse {

    private Long chequeraFacturacionElectronicaId;
    private Long chequeraId;
    private String cuit;
    private String razonSocial;
    private String domicilio;
    private String email;
    private String condicionIva;

}
