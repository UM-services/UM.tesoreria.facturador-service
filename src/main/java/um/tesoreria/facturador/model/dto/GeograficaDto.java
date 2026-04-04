package um.tesoreria.facturador.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeograficaDto {

    private Integer geograficaId;
    private String nombre = "";
    private Byte sinChequera = 0;

}
