package um.tesoreria.facturador.hexagonal.facturacion.procesos.emision.application.usecases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.application.service.ChequeraPagoService;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.domain.model.ChequeraPago;
import um.tesoreria.facturador.hexagonal.facturacion.procesos.emision.domain.ports.in.FacturarCuotaUseCase;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class FacturarPendientesUseCaseImplTest {

    @Mock
    private ChequeraPagoService chequeraPagoService;
    @Mock
    private FacturarCuotaUseCase facturarCuotaUseCase;

    @InjectMocks
    private FacturarPendientesUseCaseImpl useCase;

    @Test
    void recorreDesdeNoventaDiasAtrasHastaDosDiasAdelante() {
        when(chequeraPagoService.getPendientes(any())).thenReturn(List.of());

        assertThat(useCase.facturarPendientes()).isEqualTo("Fin proceso");

        ArgumentCaptor<OffsetDateTime> fechas = ArgumentCaptor.forClass(OffsetDateTime.class);
        verify(chequeraPagoService, times(92)).getPendientes(fechas.capture());

        OffsetDateTime hoy = OffsetDateTime.now().with(LocalTime.MIDNIGHT);
        assertThat(fechas.getAllValues().get(0)).isEqualTo(hoy.minusDays(90));
        assertThat(fechas.getAllValues().get(91)).isEqualTo(hoy.plusDays(1));
        assertThat(fechas.getAllValues()).allSatisfy(fecha ->
                assertThat(fecha.toLocalTime()).isEqualTo(LocalTime.MIDNIGHT));
    }

    @Test
    void facturaCadaPagoQueDevuelveCore() {
        when(chequeraPagoService.getPendientes(any())).thenReturn(List.of(pago()));
        when(facturarCuotaUseCase.facturarCuota(any())).thenReturn(true);

        assertThat(useCase.facturarPendientes()).isEqualTo("Fin proceso");

        verify(facturarCuotaUseCase, times(92)).facturarCuota(any());
    }

    private ChequeraPago pago() {
        return ChequeraPago.builder()
                .chequeraPagoId(100L)
                .importe(new BigDecimal("1234.50"))
                .build();
    }

}
