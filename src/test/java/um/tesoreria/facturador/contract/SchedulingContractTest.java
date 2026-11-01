package um.tesoreria.facturador.contract;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import um.tesoreria.facturador.hexagonal.facturacion.procesos.emision.infrastructure.scheduler.EmisionScheduler;
import um.tesoreria.facturador.hexagonal.facturacion.procesos.envio.infrastructure.scheduler.EnvioScheduler;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Congela los dos jobs programados: cron, zona (ausente = default) y la condicion
 * que los desactiva con app.testing=true.
 */
class SchedulingContractTest {

    @Test
    void losSchedulersSiguenSiendoComponentesCondicionadosAPorAppTesting() {
        assertThat(EmisionScheduler.class.isAnnotationPresent(Component.class)).isTrue();
        assertThat(EnvioScheduler.class.isAnnotationPresent(Component.class)).isTrue();

        assertThat(condicion(EmisionScheduler.class)).satisfies(condicion -> {
            assertThat(condicion.name()).containsExactly("app.testing");
            assertThat(condicion.havingValue()).isEqualTo("false");
            assertThat(condicion.matchIfMissing()).isTrue();
        });
        assertThat(condicion(EnvioScheduler.class)).satisfies(condicion -> {
            assertThat(condicion.name()).containsExactly("app.testing");
            assertThat(condicion.havingValue()).isEqualTo("false");
            assertThat(condicion.matchIfMissing()).isTrue();
        });
    }

    @Test
    void mantieneLosDosCronOriginales() {
        assertThat(cron(EmisionScheduler.class, "facturaPendientesScheduled")).isEqualTo("0 0 2 * * *");
        assertThat(cron(EnvioScheduler.class, "sendFacturasPendientesScheduled")).isEqualTo("0 */5 * * * *");
    }

    @Test
    void noDefineZonaExplicitaEnNingunJob() {
        assertThat(zona(EmisionScheduler.class, "facturaPendientesScheduled")).isEmpty();
        assertThat(zona(EnvioScheduler.class, "sendFacturasPendientesScheduled")).isEmpty();
    }

    private ConditionalOnProperty condicion(Class<?> type) {
        ConditionalOnProperty condicion = type.getAnnotation(ConditionalOnProperty.class);
        assertThat(condicion).as("@ConditionalOnProperty en %s", type.getSimpleName()).isNotNull();
        return condicion;
    }

    private String cron(Class<?> type, String methodName) {
        assertThat(scheduled(type, methodName)).as("método %s", methodName).isNotNull();
        return scheduled(type, methodName).cron();
    }

    private String zona(Class<?> type, String methodName) {
        return scheduled(type, methodName).zone();
    }

    private Scheduled scheduled(Class<?> type, String methodName) {
        try {
            Method method = type.getDeclaredMethod(methodName);
            return method.getAnnotation(Scheduled.class);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

}
