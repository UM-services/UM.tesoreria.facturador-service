package um.tesoreria.facturador.hexagonal.facturacion.procesos.envio.application.usecases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.application.exception.ChequeraPagoException;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.application.service.ChequeraPagoService;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.domain.model.ChequeraPago;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.application.exception.FacturacionElectronicaException;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.application.service.FacturacionElectronicaService;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.domain.model.FacturacionElectronica;
import um.tesoreria.facturador.hexagonal.facturacion.gateways.recibo.application.service.ReciboService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class EnviarReciboPorPagoUseCaseImplTest {

    private static final Long PAGO_ID = 100L;
    private static final Long FACTURACION_ID = 98L;

    @Mock
    private FacturacionElectronicaService facturacionElectronicaService;
    @Mock
    private ChequeraPagoService chequeraPagoService;
    @Mock
    private ReciboService reciboService;

    @InjectMocks
    private EnviarReciboPorPagoUseCaseImpl useCase;

    @Test
    void enviaElReciboYDevuelveLaRespuestaDelSender() {
        when(facturacionElectronicaService.getByChequeraPagoId(PAGO_ID))
                .thenReturn(FacturacionElectronica.builder().facturacionElectronicaId(FACTURACION_ID).build());
        when(chequeraPagoService.getById(PAGO_ID)).thenReturn(ChequeraPago.builder().chequeraPagoId(PAGO_ID).build());
        when(reciboService.enviar(FACTURACION_ID)).thenReturn("Enviado");

        assertThat(useCase.enviar(PAGO_ID)).isEqualTo("Enviado");

        verify(reciboService).enviar(FACTURACION_ID);
    }

    @Test
    void noEnviaSiLaFacturaNoExiste() {
        when(facturacionElectronicaService.getByChequeraPagoId(PAGO_ID))
                .thenThrow(new FacturacionElectronicaException());

        assertThat(useCase.enviar(PAGO_ID)).isEqualTo("Facturación pendiente");

        verifyNoInteractions(reciboService);
    }

    @Test
    void noEnviaSiElPagoNoExiste() {
        when(facturacionElectronicaService.getByChequeraPagoId(PAGO_ID))
                .thenReturn(FacturacionElectronica.builder().facturacionElectronicaId(FACTURACION_ID).build());
        when(chequeraPagoService.getById(PAGO_ID)).thenThrow(new ChequeraPagoException(PAGO_ID));

        assertThat(useCase.enviar(PAGO_ID)).isEqualTo("Facturación pendiente");

        verifyNoInteractions(reciboService);
    }

}
