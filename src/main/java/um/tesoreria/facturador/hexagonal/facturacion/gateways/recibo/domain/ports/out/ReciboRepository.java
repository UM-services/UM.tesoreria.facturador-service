package um.tesoreria.facturador.hexagonal.facturacion.gateways.recibo.domain.ports.out;

public interface ReciboRepository {

    String enviar(Long facturacionElectronicaId);

}
