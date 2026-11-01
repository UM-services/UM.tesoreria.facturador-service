package um.tesoreria.facturador.architecture;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifica la regla de dependencias de la arquitectura hexagonal sobre las fuentes:
 * domain puro, application sin infraestructura, slices de datos/gateways aisladas y
 * slices de proceso (patron M1) como unico punto de composicion cruzada.
 */
class ArquitecturaHexagonalTest {

    private static final Path BASE = Path.of("src/main/java/um/tesoreria/facturador/hexagonal/facturacion");
    private static final String ROOT = "um.tesoreria.facturador.hexagonal.facturacion.";
    private static final String SUPPORT = "um.tesoreria.facturador.hexagonal.facturacion.infrastructure.client.support";

    @Test
    void elDominioSoloDependeDeJavaYLombok() throws IOException {
        for (Path file : filesUnder("domain")) {
            String slice = sliceOf(file);
            boolean esModelo = file.toString().replace('\\', '/').contains("/domain/model/");
            String propio = ROOT + slice.replace('/', '.') + ".domain.model.";
            for (String dependency : imports(file)) {
                boolean permitido = dependency.matches("^(java|javax)\\..*|^lombok\\..*");
                if (!permitido && dependency.startsWith(propio)) {
                    permitido = true;
                }
                if (!permitido && !esModelo && slice.startsWith("procesos/")
                        && dependency.matches("^" + java.util.regex.Pattern.quote(ROOT)
                        + "(datos|gateways)\\..+\\.domain\\.model\\..*")) {
                    permitido = true;
                }
                assertThat(permitido)
                        .as("%s solo puede importar java.*, lombok.*, su propio modelo o (procesos) modelos de datos/gateways: %s",
                                file, dependency)
                        .isTrue();
            }
        }
    }

    @Test
    void laAplicacionNoDependeDeInfraestructuraNiDeFeignNiDeSpringWeb() throws IOException {
        for (Path file : filesUnder("application")) {
            for (String dependency : imports(file)) {
                assertThat(dependency)
                        .as("%s no puede depender de infraestructura", file)
                        .doesNotContain(".infrastructure.")
                        .doesNotStartWith("feign.")
                        .doesNotStartWith("com.fasterxml.")
                        .doesNotStartWith("jakarta.")
                        .doesNotStartWith("org.springframework.web.")
                        .doesNotStartWith("org.springframework.http.")
                        .doesNotStartWith("org.springframework.cloud.");
            }
        }
    }

    @Test
    void laAplicacionSoloUsaAnotacionesDeSpringDeStereotipoOTransaccion() throws IOException {
        for (Path file : filesUnder("application")) {
            for (String dependency : imports(file)) {
                if (dependency.startsWith("org.springframework.")) {
                    assertThat(dependency)
                            .as("%s solo puede usar @Service/@Component/@Transactional", file)
                            .matches("^org\\.springframework\\.(stereotype|transaction\\.annotation)\\..*");
                }
            }
        }
    }

    @Test
    void lasSlicesDeDatosYGatewaysNoImportanOtrasSlices() throws IOException {
        for (Path file : allFiles()) {
            String slice = sliceOf(file);
            if (!slice.startsWith("datos/") && !slice.startsWith("gateways/")) {
                continue;
            }
            for (String dependency : projectImports(file)) {
                if (dependency.startsWith(SUPPORT)) {
                    continue;
                }
                assertThat(dependency)
                        .as("%s (slice %s) no puede importar otra slice", file, slice)
                        .startsWith(ROOT + slice.replace('/', '.') + ".");
            }
        }
    }

    @Test
    void lasSlicesDeProcesoNoImportanInfraestructuraAjena() throws IOException {
        for (Path file : allFiles()) {
            String slice = sliceOf(file);
            if (!slice.startsWith("procesos/")) {
                continue;
            }
            for (String dependency : projectImports(file)) {
                if (dependency.startsWith(SUPPORT) || dependency.startsWith(ROOT + slice.replace('/', '.') + ".")) {
                    continue;
                }
                assertThat(dependency)
                        .as("%s no puede importar infraestructura de otra slice", file)
                        .doesNotContain(".infrastructure.");
            }
        }
    }

    @Test
    void losControladoresNoExponenTiposDeInfraestructura() throws IOException {
        for (Path file : filesUnder("infrastructure/web/controller")) {
            String source = Files.readString(file);
            assertThat(source)
                    .as("%s no puede depender de persistencia ni exponer entidades JPA", file)
                    .doesNotContain(".persistence.")
                    .doesNotContain("Jpa");
        }
    }

    @Test
    void losPaquetesLegadosYaNoExisten() {
        Path root = Path.of("src/main/java/um/tesoreria/facturador");
        assertThat(root.resolve("controller")).doesNotExist();
        assertThat(root.resolve("service")).doesNotExist();
        assertThat(root.resolve("client")).doesNotExist();
        assertThat(root.resolve("model")).doesNotExist();
        assertThat(root.resolve("tool")).doesNotExist();
    }

    private List<Path> allFiles() throws IOException {
        try (Stream<Path> files = Files.walk(BASE)) {
            return files.filter(path -> path.toString().endsWith(".java")).toList();
        }
    }

    private List<Path> filesUnder(String fragment) throws IOException {
        return allFiles().stream()
                .filter(path -> path.toString().replace('\\', '/').contains("/" + fragment + "/"))
                .toList();
    }

    private List<String> imports(Path file) throws IOException {
        return Files.readAllLines(file).stream()
                .map(String::trim)
                .filter(line -> line.startsWith("import "))
                .map(line -> line.substring("import ".length(), line.length() - 1))
                .filter(line -> !line.startsWith("static "))
                .toList();
    }

    private List<String> projectImports(Path file) throws IOException {
        return imports(file).stream()
                .filter(dependency -> dependency.startsWith(ROOT))
                .toList();
    }

    private String sliceOf(Path file) {
        String relative = BASE.relativize(file).toString().replace('\\', '/');
        String[] parts = relative.split("/");
        return parts[0] + "/" + parts[1];
    }

}
