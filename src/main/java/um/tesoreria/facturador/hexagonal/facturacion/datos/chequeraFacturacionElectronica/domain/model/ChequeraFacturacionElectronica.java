package um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraFacturacionElectronica.domain.model;

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
public class ChequeraFacturacionElectronica {

    private Long chequeraFacturacionElectronicaId;
    private Long chequeraId;
    @Builder.Default
    private String cuit = "";
    @Builder.Default
    private String razonSocial = "";
    @Builder.Default
    private String domicilio = "";
    @Builder.Default
    private String email = "";
    @Builder.Default
    private String condicionIva = "";

}
