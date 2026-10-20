package um.tesoreria.facturador.hexagonal.facturacion.infrastructure.client.support;

public interface Jsonifyable {

    default String jsonify() {
        return Jsonifier.builder(this).build();
    }

}
