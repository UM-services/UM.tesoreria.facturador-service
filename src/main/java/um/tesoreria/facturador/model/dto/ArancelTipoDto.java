package um.tesoreria.facturador.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArancelTipoDto {

    private Integer arancelTipoId;
    private String descripcion = "";
    private Byte medioArancel = 0;
    private Integer arancelTipoIdCompleto;

}
