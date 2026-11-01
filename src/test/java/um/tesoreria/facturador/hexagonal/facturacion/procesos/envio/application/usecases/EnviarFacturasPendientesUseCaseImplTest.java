package um.tesoreria.facturador.hexagonal.facturacion.procesos.envio.application.usecases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.application.service.FacturacionElectronicaService;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.domain.model.FacturacionElectronica;
import um.tesoreria.facturador.hexagonal.facturacion.procesos.envio.domain.ports.in.EnviarReciboPorFacturacionUseCase;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class EnviarFacturasPendientesUseCaseImplTest {

    @Mock
    private FacturacionElectronicaService facturacionElectronicaService;
    @Mock
    private EnviarReciboPorFacturacionUseCase enviarReciboPorFacturacionUseCase;

    @InjectMocks
    private EnviarFacturasPendientesUseCaseImpl useCase;

    @Test
    void enviaTodasLasFacturasPendientes() {
        when(facturacionElectronicaService.getPendientes()).thenReturn(List.of(
                FacturacionElectronica.builder().facturacionElectronicaId(98L).build(),
                FacturacionElectronica.builder().facturacionElectronicaId(99L).build()));

        useCase.enviarPendientes();

        verify(enviarReciboPorFacturacionUseCase).enviar(98L);
        verify(enviarReciboPorFacturacionUseCase).enviar(99L);
    }

}
