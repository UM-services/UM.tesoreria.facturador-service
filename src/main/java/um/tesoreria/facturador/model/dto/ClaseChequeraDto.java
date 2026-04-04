package um.tesoreria.facturador.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClaseChequeraDto {

    private Integer claseChequeraId;
    private String nombre;
    private Byte preuniversitario = 0;
    private Byte grado = 0;
    private Byte posgrado = 0;
    private Byte curso = 0;
    private Byte secundario = 0;
    private Byte titulo = 0;

}
