package um.tesoreria.facturador.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComprobanteAfipDto {

    private Integer comprobanteAfipId;
    private String nombre = "";
    private String label = "";

}
