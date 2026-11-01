package um.tesoreria.facturador.contract;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import um.tesoreria.facturador.hexagonal.facturacion.procesos.emision.application.service.EmisionService;
import um.tesoreria.facturador.hexagonal.facturacion.procesos.emision.infrastructure.web.controller.EmisionController;
import um.tesoreria.facturador.hexagonal.facturacion.procesos.envio.application.service.EnvioService;
import um.tesoreria.facturador.hexagonal.facturacion.procesos.envio.infrastructure.web.controller.EnvioController;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Congela el contrato HTTP observable del facturador: rutas, verbos y el cuerpo
 * literal (String) de cada respuesta. Debe seguir verde sin cambios tras el refactor.
 */
class FacturadorHttpContractTest {

    private static final String BASE = "/api/tesoreria/facturador";

    private EmisionService emisionService;
    private EnvioService envioService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        emisionService = mock(EmisionService.class);
        envioService = mock(EnvioService.class);
        mockMvc = MockMvcBuilders
                .standaloneSetup(new EmisionController(emisionService), new EnvioController(envioService))
                .build();
    }

    @Test
    void declaraLosMismosControladoresYLasMismasRutas() {
        assertThat(EmisionController.class.isAnnotationPresent(RestController.class)).isTrue();
        assertThat(EnvioController.class.isAnnotationPresent(RestController.class)).isTrue();
        assertThat(EmisionController.class.getAnnotation(RequestMapping.class).value()).containsExactly(BASE);
        assertThat(EnvioController.class.getAnnotation(RequestMapping.class).value()).containsExactly(BASE);

        Map<String, String> rutas = Stream.of(
                        rutasDeclaradas(EmisionController.class),
                        rutasDeclaradas(EnvioController.class))
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        assertThat(rutas).containsEntry("facturaPendientes", "/facturaPendientes")
                .containsEntry("facturaOne", "/facturaOne/{chequeraPagoId}")
                .containsEntry("sendOneByChequeraPagoId", "/sendOne/pago/{chequeraPagoId}")
                .containsEntry("sendOneByFacturacionElectronicaId", "/sendOne/recibo/{facturacionElectronicaId}");
        assertThat(rutas).hasSize(4);
    }

    @Test
    void facturaPendientesDevuelveElCuerpoLiteral() throws Exception {
        when(emisionService.facturaPendientes()).thenReturn("Fin proceso");

        mockMvc.perform(get(BASE + "/facturaPendientes"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("text/plain"))
                .andExpect(content().string("Fin proceso"));

        verify(emisionService).facturaPendientes();
    }

    @Test
    void facturaOneDevuelveElCuerpoLiteralYPasaElId() throws Exception {
        when(emisionService.facturaOne(17L)).thenReturn("Facturado Ok");

        mockMvc.perform(get(BASE + "/facturaOne/17"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("text/plain"))
                .andExpect(content().string("Facturado Ok"));

        verify(emisionService).facturaOne(17L);
    }

    @Test
    void sendOnePorPagoDevuelveElCuerpoLiteralYPasaElId() throws Exception {
        when(envioService.sendOneByChequeraPagoId(23L)).thenReturn("Facturación pendiente");

        mockMvc.perform(get(BASE + "/sendOne/pago/23"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("text/plain"))
                .andExpect(content().string("Facturación pendiente"));

        verify(envioService).sendOneByChequeraPagoId(23L);
    }

    @Test
    void sendOnePorReciboDevuelveElCuerpoLiteralYPasaElId() throws Exception {
        when(envioService.sendOneByFacturacionElectronicaId(29L)).thenReturn("Error de envío");

        mockMvc.perform(get(BASE + "/sendOne/recibo/29"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("text/plain"))
                .andExpect(content().string("Error de envío"));

        verify(envioService).sendOneByFacturacionElectronicaId(29L);
    }

    @Test
    void losCuatroEndpointsSonSoloGet() throws Exception {
        mockMvc.perform(post(BASE + "/facturaPendientes")).andExpect(status().isMethodNotAllowed());
        mockMvc.perform(post(BASE + "/facturaOne/17")).andExpect(status().isMethodNotAllowed());
        mockMvc.perform(post(BASE + "/sendOne/pago/23")).andExpect(status().isMethodNotAllowed());
        mockMvc.perform(post(BASE + "/sendOne/recibo/29")).andExpect(status().isMethodNotAllowed());
    }

    private Map<String, String> rutasDeclaradas(Class<?> controller) {
        return Arrays.stream(controller.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(GetMapping.class))
                .collect(Collectors.toMap(
                        Method::getName,
                        method -> method.getAnnotation(GetMapping.class).value()[0]));
    }

}
