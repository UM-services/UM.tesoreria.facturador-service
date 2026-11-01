package um.tesoreria.facturador.hexagonal.facturacion.procesos.envio.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import um.tesoreria.facturador.hexagonal.facturacion.procesos.envio.domain.ports.in.EnviarFacturasPendientesUseCase;
import um.tesoreria.facturador.hexagonal.facturacion.procesos.envio.domain.ports.in.EnviarReciboPorFacturacionUseCase;
import um.tesoreria.facturador.hexagonal.facturacion.procesos.envio.domain.ports.in.EnviarReciboPorPagoUseCase;

@Service
@RequiredArgsConstructor
public class EnvioService {

    private final EnviarReciboPorPagoUseCase enviarReciboPorPagoUseCase;
    private final EnviarReciboPorFacturacionUseCase enviarReciboPorFacturacionUseCase;
    private final EnviarFacturasPendientesUseCase enviarFacturasPendientesUseCase;

    public String sendOneByChequeraPagoId(Long chequeraPagoId) {
        return enviarReciboPorPagoUseCase.enviar(chequeraPagoId);
    }

    public String sendOneByFacturacionElectronicaId(Long facturacionElectronicaId) {
        return enviarReciboPorFacturacionUseCase.enviar(facturacionElectronicaId);
    }

    public void sendFacturasPendientes() {
        enviarFacturasPendientesUseCase.enviarPendientes();
    }

}
