package um.tesoreria.facturador.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import um.tesoreria.facturador.tool.Jsonifier;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChequeraFacturacionElectronicaDto {

    private Long chequeraFacturacionElectronicaId;
    private Long chequeraId;
    private String cuit = "";
    private String razonSocial = "";
    private String domicilio = "";
    private String email = "";
    private String condicionIva = "";

    public String jsonify() {
        return Jsonifier.builder(this).build();
    }

}
