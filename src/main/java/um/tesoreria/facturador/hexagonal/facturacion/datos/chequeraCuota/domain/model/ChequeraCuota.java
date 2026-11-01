package um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraCuota.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChequeraCuota {

    private Long chequeraId;
    private BigDecimal personaId;
    private String personaApellido;
    private String personaNombre;

}
