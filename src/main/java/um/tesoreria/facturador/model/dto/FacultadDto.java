package um.tesoreria.facturador.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FacultadDto {

    private Integer facultadId;
    private String nombre = "";
    private String codigoempresa = "";
    private String server = "";
    private String dbadm = "";
    private String dsn = "";
    private BigDecimal cuentacontable = BigDecimal.ZERO;
    private String apiserver = "";
    private Long apiport = 0L;

}
