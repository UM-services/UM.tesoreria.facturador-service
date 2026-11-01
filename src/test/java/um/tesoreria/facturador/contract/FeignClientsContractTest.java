package um.tesoreria.facturador.contract;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import feign.Client;
import feign.Feign;
import feign.Request;
import feign.Response;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.format.support.DefaultFormattingConversionService;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraCuota.infrastructure.client.ChequeraCuotaClient;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraFacturacionElectronica.infrastructure.client.ChequeraFacturacionElectronicaClient;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.infrastructure.client.ChequeraPagoClient;
import um.tesoreria.facturador.hexagonal.facturacion.datos.comprobante.infrastructure.client.ComprobanteClient;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.infrastructure.client.FacturacionElectronicaClient;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.infrastructure.client.dto.FacturacionElectronicaDto;
import um.tesoreria.facturador.hexagonal.facturacion.gateways.afip.infrastructure.client.FacturacionAfipClient;
import um.tesoreria.facturador.hexagonal.facturacion.gateways.afip.infrastructure.client.dto.FacturacionAfipDto;
import um.tesoreria.facturador.hexagonal.facturacion.gateways.recibo.infrastructure.client.ReciboClient;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Congela el contrato HTTP saliente: service-id de cada {@code @FeignClient}, verbo,
 * URL final (path incluido) y cuerpo JSON de los POST. Debe seguir verde tras el refactor.
 */
class FeignClientsContractTest {

    private static final ObjectMapper MAPPER = JsonMapper.builder().findAndAddModules().build();

    private RecordingClient http;
    private RecordingEncoder encoder;
    private Feign.Builder feign;

    @BeforeEach
    void setUp() {
        http = new RecordingClient();
        encoder = new RecordingEncoder();
        var contract = new SpringMvcContract(Collections.emptyList(), new DefaultFormattingConversionService());
        Decoder decoder = (response, type) -> type == String.class ? "" : null;
        feign = Feign.builder().contract(contract).client(http).encoder(encoder).decoder(decoder);
    }

    @Test
    void declaraLosMismosServiceIds() {
        assertThat(servicePath(ChequeraPagoClient.class))
                .isEqualTo("tesoreria-core-service/api/tesoreria/core/chequeraPago");
        assertThat(servicePath(ChequeraCuotaClient.class))
                .isEqualTo("tesoreria-core-service/api/tesoreria/core/chequeraCuota");
        assertThat(servicePath(ComprobanteClient.class))
                .isEqualTo("tesoreria-core-service/api/tesoreria/core/comprobante");
        assertThat(servicePath(ChequeraFacturacionElectronicaClient.class))
                .isEqualTo("tesoreria-core-service/api/tesoreria/core/chequeraFacturacionElectronica");
        assertThat(servicePath(FacturacionElectronicaClient.class))
                .isEqualTo("tesoreria-core-service/api/tesoreria/core/facturacionElectronica");
        assertThat(servicePath(FacturacionAfipClient.class))
                .isEqualTo("pyafipws-service/api/afipws");
        assertThat(servicePath(ReciboClient.class))
                .isEqualTo("tesoreria-sender-service/api/tesoreria/sender/recibo");
    }

    @Test
    void chequeraPagoMantieneSusRutas() {
        var fecha = OffsetDateTime.of(2026, 7, 22, 0, 0, 0, 0, ZoneOffset.ofHours(-3));

        target(ChequeraPagoClient.class).pendientesFactura(fecha);
        assertThat(http.last.httpMethod()).isEqualTo(Request.HttpMethod.GET);
        assertThat(http.last.url()).isEqualTo(base(ChequeraPagoClient.class)
                + "/pendientesFactura/2026-07-22T00%3A00%3A00-03%3A00");

        target(ChequeraPagoClient.class).findByChequeraPagoId(99L);
        assertThat(http.last.httpMethod()).isEqualTo(Request.HttpMethod.GET);
        assertThat(http.last.url()).isEqualTo(base(ChequeraPagoClient.class) + "/99");
    }

    @Test
    void chequeraCuotaMantieneSusRutas() {
        var desde = OffsetDateTime.of(2026, 7, 22, 0, 0, 0, 0, ZoneOffset.ofHours(-3));
        var hasta = OffsetDateTime.of(2026, 7, 23, 0, 0, 0, 0, ZoneOffset.ofHours(-3));

        target(ChequeraCuotaClient.class).findAllByChequera(1, 2, 3L, 4);
        assertThat(http.last.httpMethod()).isEqualTo(Request.HttpMethod.GET);
        assertThat(http.last.url()).isEqualTo(base(ChequeraCuotaClient.class) + "/chequera/1/2/3/4");

        target(ChequeraCuotaClient.class).findAllInconsistencias(desde, hasta);
        assertThat(http.last.url()).isEqualTo(base(ChequeraCuotaClient.class)
                + "/inconsistencias/2026-07-22T00%3A00%3A00-03%3A00/2026-07-23T00%3A00%3A00-03%3A00");

        target(ChequeraCuotaClient.class).findByChequeraCuotaId(7L);
        assertThat(http.last.url()).isEqualTo(base(ChequeraCuotaClient.class) + "/7");

        target(ChequeraCuotaClient.class).findByUnique(1, 2, 3L, 4, 5, 6);
        assertThat(http.last.url()).isEqualTo(base(ChequeraCuotaClient.class) + "/unique/1/2/3/4/5/6");
    }

    @Test
    void comprobanteYChequeraFacturacionElectronicaMantienenSusRutas() {
        target(ComprobanteClient.class).findByComprobanteId(14);
        assertThat(http.last.httpMethod()).isEqualTo(Request.HttpMethod.GET);
        assertThat(http.last.url()).isEqualTo(base(ComprobanteClient.class) + "/14");

        target(ChequeraFacturacionElectronicaClient.class).findByChequeraId(10L);
        assertThat(http.last.url()).isEqualTo(base(ChequeraFacturacionElectronicaClient.class) + "/chequera/10");
    }

    @Test
    void facturacionElectronicaMantieneSusRutas() {
        target(FacturacionElectronicaClient.class).findAllByChequera(1, 2, 3L);
        assertThat(http.last.url()).isEqualTo(base(FacturacionElectronicaClient.class) + "/chequera/1/2/3");

        target(FacturacionElectronicaClient.class).find3Pendientes();
        assertThat(http.last.url()).isEqualTo(base(FacturacionElectronicaClient.class) + "/pendientes");

        target(FacturacionElectronicaClient.class).findByFacturacionElectronicaId(98L);
        assertThat(http.last.url()).isEqualTo(base(FacturacionElectronicaClient.class) + "/98");

        target(FacturacionElectronicaClient.class).findByChequeraPagoId(99L);
        assertThat(http.last.url()).isEqualTo(base(FacturacionElectronicaClient.class) + "/pago/99");
    }

    @Test
    void afipMantieneSusRutasYElCuerpoSnakeCase() throws Exception {
        target(FacturacionAfipClient.class).test();
        assertThat(http.last.httpMethod()).isEqualTo(Request.HttpMethod.GET);
        assertThat(http.last.url()).isEqualTo(base(FacturacionAfipClient.class) + "/test");

        var solicitud = FacturacionAfipDto.builder()
                .tipoDocumento(80)
                .documento("30518594466")
                .tipoAfip(11)
                .puntoVenta(4)
                .total(new BigDecimal("1234.50"))
                .neto(new BigDecimal("1234.50"))
                .idCondicionIva(5)
                .build();

        target(FacturacionAfipClient.class).facturador(solicitud);
        assertThat(http.last.httpMethod()).isEqualTo(Request.HttpMethod.POST);
        assertThat(http.last.url()).isEqualTo(base(FacturacionAfipClient.class) + "/facturador");
        JSONAssert.assertEquals("""
                {"documento":"30518594466","total":1234.50,"exento":0,"neto":1234.50,"neto105":0,
                 "iva":0,"iva105":0,"resultado":"","cae":"","tipo_documento":80,"tipo_afip":11,
                 "punto_venta":4,"vencimiento_cae":"","numero_comprobante":0,
                 "asociado_tipo_afip":null,"asociado_punto_venta":null,
                 "asociado_numero_comprobante":null,"asociado_fecha_comprobante":null,
                 "id_condicion_iva":5}
                """, encoder.bodyJson(), true);
    }

    @Test
    void elAltaDeFacturacionElectronicaMantieneSuRutaYSuPayload() throws Exception {
        var factura = FacturacionElectronicaDto.builder()
                .chequeraPagoId(1L)
                .comprobanteId(14)
                .numeroComprobante(77L)
                .personaId(new BigDecimal("4500"))
                .tipoDocumento("CUIT")
                .apellido("UNIVERSIDAD DE MENDOZA")
                .nombre("")
                .cuit("30518594466")
                .condicionIva("Responsable Inscripto")
                .importe(new BigDecimal("1234.50"))
                .cae("75123456789012")
                .fechaRecibo(OffsetDateTime.of(2026, 10, 20, 0, 0, 0, 0, ZoneOffset.UTC))
                .fechaVencimientoCae(OffsetDateTime.of(2026, 10, 31, 0, 0, 0, 0, ZoneOffset.UTC))
                .build();

        target(FacturacionElectronicaClient.class).add(factura);

        assertThat(http.last.httpMethod()).isEqualTo(Request.HttpMethod.POST);
        assertThat(http.last.url()).isEqualTo(base(FacturacionElectronicaClient.class) + "/");
        JSONAssert.assertEquals("""
                {"facturacionElectronicaId":null,"chequeraPagoId":1,"comprobanteId":14,
                 "numeroComprobante":77,"personaId":4500,"tipoDocumento":"CUIT",
                 "apellido":"UNIVERSIDAD DE MENDOZA","nombre":"","cuit":"30518594466",
                 "condicionIva":"Responsable Inscripto","importe":1234.50,"cae":"75123456789012",
                 "fechaRecibo":"2026-10-20T00:00:00Z","fechaVencimientoCae":"2026-10-31T00:00:00Z",
                 "enviada":0,"retries":0,"chequeraPago":null,"comprobante":null}
                """, encoder.bodyJson(), true);
    }

    @Test
    void reciboMantieneSuRuta() {
        target(ReciboClient.class).send(98L);
        assertThat(http.last.httpMethod()).isEqualTo(Request.HttpMethod.GET);
        assertThat(http.last.url()).isEqualTo(base(ReciboClient.class) + "/send/98");
    }

    private <T> T target(Class<T> type) {
        return feign.target(type, base(type));
    }

    private String base(Class<?> type) {
        return "http://" + servicePath(type);
    }

    private String servicePath(Class<?> type) {
        FeignClient annotation = type.getAnnotation(FeignClient.class);
        assertThat(annotation).as("@FeignClient en %s", type.getSimpleName()).isNotNull();
        return annotation.value().isEmpty() ? annotation.name() : annotation.value();
    }

    private static final class RecordingClient implements Client {
        private Request last;

        @Override
        public Response execute(Request request, Request.Options options) {
            this.last = request;
            return Response.builder()
                    .status(200)
                    .reason("OK")
                    .request(request)
                    .headers(Map.of())
                    .build();
        }
    }

    private static final class RecordingEncoder implements Encoder {
        private Object lastBody;

        @Override
        public void encode(Object object, java.lang.reflect.Type bodyType, feign.RequestTemplate template) {
            this.lastBody = object;
        }

        String bodyJson() throws Exception {
            return MAPPER.writeValueAsString(lastBody);
        }
    }

}
