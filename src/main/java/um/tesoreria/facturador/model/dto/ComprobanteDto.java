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
public class ComprobanteDto {

    private Integer comprobanteId;
    private String descripcion = "";
    private Integer tipoTransaccionId;
    private Byte ordenPago = 0;
    private Byte aplicaPendiente = 0;
    private Byte cuentaCorriente = 0;
    private Byte debita = 0;
    private Long diasVigencia = 0L;
    private Byte facturacionElectronica = 0;
    private Integer comprobanteAfipId;
    private Integer puntoVenta;
    private String letraComprobante;
    private ComprobanteAfipDto comprobanteAfip;

    public String jsonify() {
        return Jsonifier.builder(this).build();
    }

}
