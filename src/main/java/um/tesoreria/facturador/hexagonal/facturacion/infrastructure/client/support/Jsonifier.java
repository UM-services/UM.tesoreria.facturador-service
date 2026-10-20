package um.tesoreria.facturador.hexagonal.facturacion.infrastructure.client.support;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.json.JsonMapper;

public final class Jsonifier {

    private Jsonifier() {
    }

    public static <T> Builder<T> builder(T object) {
        return new Builder<>(object);
    }

    public static class Builder<T> {
        private final T object;
        private boolean prettyPrint = true;

        private Builder(T object) {
            this.object = object;
        }

        public Builder<T> pretty(boolean pretty) {
            this.prettyPrint = pretty;
            return this;
        }

        public String build() {
            return jsonify();
        }

        private String jsonify() {
            try {
                ObjectMapper mapper = JsonMapper.builder()
                        .findAndAddModules()
                        .build();

                ObjectWriter writer = prettyPrint
                        ? mapper.writerWithDefaultPrettyPrinter()
                        : mapper.writer();

                return writer.writeValueAsString(object);
            } catch (JsonProcessingException e) {
                return "jsonify error: " + e.getMessage();
            }
        }
    }
}
