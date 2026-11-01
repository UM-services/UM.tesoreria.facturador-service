package um.tesoreria.facturador.hexagonal.facturacion.procesos.emision.application.usecases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.application.service.ChequeraPagoService;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.domain.model.ChequeraPago;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.application.exception.FacturacionElectronicaException;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.application.service.FacturacionElectronicaService;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.domain.model.FacturacionElectronica;
import um.tesoreria.facturador.hexagonal.facturacion.procesos.emision.domain.ports.in.FacturarCuotaUseCase;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class FacturarPagoUseCaseImplTest {

    private static final Long PAGO_ID = 100L;

    @Mock
    private FacturacionElectronicaService facturacionElectronicaService;
    @Mock
    private ChequeraPagoService chequeraPagoService;
    @Mock
    private FacturarCuotaUseCase facturarCuotaUseCase;

    @InjectMocks
    private FacturarPagoUseCaseImpl useCase;

    @Test
    void conFacturaPreviaNoConsultaElPagoNiFactura() {
        when(facturacionElectronicaService.getByChequeraPagoId(PAGO_ID))
                .thenReturn(FacturacionElectronica.builder().facturacionElectronicaId(98L).build());

        assertThat(useCase.facturarPago(PAGO_ID)).isEqualTo("ERROR: Facturación previa");

        verifyNoInteractions(chequeraPagoService, facturarCuotaUseCase);
    }

    @Test
    void devuelveFacturadoOkCuandoLaCuotaSeFactura() {
        stubPagoPendiente();
        when(facturarCuotaUseCase.facturarCuota(anyPago())).thenReturn(true);

        assertThat(useCase.facturarPago(PAGO_ID)).isEqualTo("Facturado Ok");

        verify(chequeraPagoService).getById(PAGO_ID);
    }

    @Test
    void devuelveFacturadoNoCuandoLaCuotaNoSeFactura() {
        stubPagoPendiente();
        when(facturarCuotaUseCase.facturarCuota(anyPago())).thenReturn(false);

        assertThat(useCase.facturarPago(PAGO_ID)).isEqualTo("Facturado NO");
    }

    private void stubPagoPendiente() {
        when(facturacionElectronicaService.getByChequeraPagoId(PAGO_ID))
                .thenThrow(new FacturacionElectronicaException(PAGO_ID));
        when(chequeraPagoService.getById(PAGO_ID)).thenReturn(pago());
    }

    private ChequeraPago anyPago() {
        return org.mockito.ArgumentMatchers.any(ChequeraPago.class);
    }

    private ChequeraPago pago() {
        return ChequeraPago.builder()
                .chequeraPagoId(PAGO_ID)
                .importe(new BigDecimal("1234.50"))
                .build();
    }

}
