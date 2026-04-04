package um.tesoreria.facturador.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TipoChequeraDto {

    private Integer tipoChequeraId;
    private String nombre = "";
    private String prefijo = "";
    private Integer geograficaId = 1;
    private Integer claseChequeraId = 2;
    private Byte imprimir = 0;
    private Byte contado = 0;
    private Byte multiple = 0;
    private String emailCopia;
    private GeograficaDto geografica;
    private ClaseChequeraDto claseChequera;

}
