package um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.infrastructure.client.mapper;

import org.junit.jupiter.api.Test;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.infrastructure.client.dto.ChequeraPagoResponse;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ChequeraPagoMapperTest {

    private final ChequeraPagoMapper mapper = new ChequeraPagoMapper();

    @Test
    void sinImporteUsaCeroComoElDtoOriginal() {
        var response = ChequeraPagoResponse.builder().chequeraPagoId(100L).build();

        assertThat(mapper.toDomain(response).getImporte()).isEqualByComparingTo("0");
    }

    @Test
    void aplanaChequeraYPersonaCuandoLaCuotaVieneEnElPago() {
        var persona = ChequeraPagoResponse.PersonaResponse.builder()
                .personaId(new BigDecimal("4500"))
                .apellido("Perez")
                .nombre("Ana")
                .build();
        var serie = ChequeraPagoResponse.ChequeraSerieResponse.builder()
                .chequeraId(10L)
                .persona(persona)
                .build();
        var cuota = ChequeraPagoResponse.ChequeraCuotaResponse.builder()
                .chequeraId(10L)
                .chequeraSerie(serie)
                .build();
        var response = ChequeraPagoResponse.builder()
                .chequeraPagoId(100L)
                .importe(new BigDecimal("1234.50"))
                .chequeraCuota(cuota)
                .build();

        var pago = mapper.toDomain(response);

        assertThat(pago.isTieneCuota()).isTrue();
        assertThat(pago.getChequeraId()).isEqualTo(10L);
        assertThat(pago.getPersonaId()).isEqualByComparingTo("4500");
        assertThat(pago.getPersonaApellido()).isEqualTo("Perez");
        assertThat(pago.getPersonaNombre()).isEqualTo("Ana");
        assertThat(pago.getImporte()).isEqualByComparingTo("1234.50");
    }

    @Test
    void marcaSinCuotaCuandoElPagoNoLaTrae() {
        var response = ChequeraPagoResponse.builder().chequeraPagoId(100L).build();

        var pago = mapper.toDomain(response);

        assertThat(pago.isTieneCuota()).isFalse();
        assertThat(pago.getChequeraId()).isNull();
        assertThat(pago.getPersonaId()).isNull();
    }

}
