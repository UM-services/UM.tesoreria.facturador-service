package um.tesoreria.facturador.hexagonal.facturacion.procesos.envio.domain.ports.in;

public interface EnviarReciboPorPagoUseCase {

    String enviar(Long chequeraPagoId);

}
