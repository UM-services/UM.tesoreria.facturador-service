package um.tesoreria.facturador.contract;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.infrastructure.client.dto.FacturacionElectronicaDto;
import um.tesoreria.facturador.hexagonal.facturacion.gateways.afip.infrastructure.client.dto.FacturacionAfipDto;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Congela el formato de wire de los DTOs que cruzan a AFIP y a core: nombres de campo,
 * valores por defecto (que hoy viajan en el body) y deserializacion de la respuesta.
 */
class WireFormatContractTest {

    private static final ObjectMapper MAPPER = JsonMapper.builder().findAndAddModules().build();

    @Test
    void laSolicitudAfipViajaConLosMismosValoresPorDefecto() throws Exception {
        JSONAssert.assertEquals("""
                {"documento":"0","total":0,"exento":0,"neto":0,"neto105":0,"iva":0,"iva105":0,
                 "resultado":"","cae":"","tipo_documento":0,"tipo_afip":0,"punto_venta":0,
                 "vencimiento_cae":"","numero_comprobante":0,"asociado_tipo_afip":null,
                 "asociado_punto_venta":null,"asociado_numero_comprobante":null,
                 "asociado_fecha_comprobante":null,"id_condicion_iva":null}
                """, MAPPER.writeValueAsString(FacturacionAfipDto.builder().build()), true);
    }

    @Test
    void deserializaLaRespuestaAfipConLosMismosNombres() throws Exception {
        FacturacionAfipDto respuesta = MAPPER.readValue("""
                {"tipo_documento":80,"documento":"30518594466","tipo_afip":11,"punto_venta":4,
                 "total":1234.50,"exento":0,"neto":1234.50,"neto105":0,"iva":0,"iva105":0,
                 "resultado":"A","cae":"75123456789012","vencimiento_cae":"20261031",
                 "numero_comprobante":77,"asociado_tipo_afip":null,"asociado_punto_venta":null,
                 "asociado_numero_comprobante":null,"asociado_fecha_comprobante":null,
                 "id_condicion_iva":5}
                """, FacturacionAfipDto.class);

        assertThat(respuesta.getResultado()).isEqualTo("A");
        assertThat(respuesta.getCae()).isEqualTo("75123456789012");
        assertThat(respuesta.getVencimientoCae()).isEqualTo("20261031");
        assertThat(respuesta.getNumeroComprobante()).isEqualTo(77L);
        assertThat(respuesta.getTipoDocumento()).isEqualTo(80);
        assertThat(respuesta.getTipoAfip()).isEqualTo(11);
        assertThat(respuesta.getPuntoVenta()).isEqualTo(4);
        assertThat(respuesta.getIdCondicionIva()).isEqualTo(5);
    }

    @Test
    void laFacturaElectronicaConservaLosDefaultsQueViajanACore() throws Exception {
        JSONAssert.assertEquals("""
                {"facturacionElectronicaId":null,"chequeraPagoId":1,"comprobanteId":null,
                 "numeroComprobante":0,"personaId":null,"tipoDocumento":null,"apellido":null,
                 "nombre":null,"cuit":null,"condicionIva":"","importe":0,"cae":null,
                 "fechaRecibo":null,"fechaVencimientoCae":null,"enviada":0,"retries":0,
                 "chequeraPago":null,"comprobante":null}
                """, MAPPER.writeValueAsString(FacturacionElectronicaDto.builder().chequeraPagoId(1L).build()), true);
    }

    @Test
    void elImporteDeLaFacturaConservaEscalaYValorOriginal() throws Exception {
        var factura = FacturacionElectronicaDto.builder()
                .chequeraPagoId(1L)
                .importe(new BigDecimal("1234.565"))
                .build();
        assertThat(factura.getImporte()).isEqualByComparingTo("1234.565");
        assertThat(MAPPER.readTree(MAPPER.writeValueAsString(factura)).get("importe").decimalValue())
                .isEqualByComparingTo("1234.565");
    }

}
