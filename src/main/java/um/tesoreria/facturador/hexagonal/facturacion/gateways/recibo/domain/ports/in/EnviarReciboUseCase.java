package um.tesoreria.facturador.hexagonal.facturacion.gateways.recibo.domain.ports.in;

public interface EnviarReciboUseCase {

    String enviar(Long facturacionElectronicaId);

}
