package um.tesoreria.facturador.hexagonal.facturacion.procesos.envio.domain.ports.in;

public interface EnviarReciboPorFacturacionUseCase {

    String enviar(Long facturacionElectronicaId);

}
