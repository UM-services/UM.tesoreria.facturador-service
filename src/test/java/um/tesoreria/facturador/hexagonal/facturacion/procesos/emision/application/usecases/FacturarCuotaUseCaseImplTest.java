package um.tesoreria.facturador.hexagonal.facturacion.procesos.emision.application.usecases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraCuota.application.service.ChequeraCuotaService;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraCuota.domain.model.ChequeraCuota;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraFacturacionElectronica.application.service.ChequeraFacturacionElectronicaService;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraFacturacionElectronica.domain.model.ChequeraFacturacionElectronica;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraPago.domain.model.ChequeraPago;
import um.tesoreria.facturador.hexagonal.facturacion.datos.comprobante.application.service.ComprobanteService;
import um.tesoreria.facturador.hexagonal.facturacion.datos.comprobante.domain.model.Comprobante;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.application.service.FacturacionElectronicaService;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.domain.model.FacturacionElectronica;
import um.tesoreria.facturador.hexagonal.facturacion.gateways.afip.application.service.FacturacionAfipService;
import um.tesoreria.facturador.hexagonal.facturacion.gateways.afip.domain.model.RespuestaFacturacionAfip;
import um.tesoreria.facturador.hexagonal.facturacion.gateways.afip.domain.model.SolicitudFacturacionAfip;
import um.tesoreria.facturador.hexagonal.facturacion.procesos.emision.application.exception.FacturacionException;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class FacturarCuotaUseCaseImplTest {

    private static final Long PAGO_ID = 100L;
    private static final Long CHEQUERA_ID = 10L;

    @Mock
    private ChequeraCuotaService chequeraCuotaService;
    @Mock
    private ComprobanteService comprobanteService;
    @Mock
    private ChequeraFacturacionElectronicaService chequeraFacturacionElectronicaService;
    @Mock
    private FacturacionElectronicaService facturacionElectronicaService;
    @Mock
    private FacturacionAfipService facturacionAfipService;

    @InjectMocks
    private FacturarCuotaUseCaseImpl useCase;

    @Test
    void conCuitEmiteYPersisteLaFactura() {
        stub(cfe());
        stubAfip(respuesta("A"));

        assertThat(useCase.facturarCuota(pago(true))).isTrue();

        var solicitud = capturarSolicitud();
        assertThat(solicitud.getTipoDocumento()).isEqualTo(80);
        assertThat(solicitud.getDocumento()).isEqualTo("30518594466");
        assertThat(solicitud.getTipoAfip()).isEqualTo(11);
        assertThat(solicitud.getPuntoVenta()).isEqualTo(4);
        assertThat(solicitud.getTotal()).isEqualByComparingTo("1234.50");
        assertThat(solicitud.getNeto()).isEqualByComparingTo("1234.50");
        assertThat(solicitud.getIdCondicionIva()).isEqualTo(5);

        var factura = capturarFactura();
        assertThat(factura.getChequeraPagoId()).isEqualTo(PAGO_ID);
        assertThat(factura.getComprobanteId()).isEqualTo(14);
        assertThat(factura.getNumeroComprobante()).isEqualTo(77L);
        assertThat(factura.getPersonaId()).isEqualByComparingTo("4500");
        assertThat(factura.getTipoDocumento()).isEqualTo("CUIT");
        assertThat(factura.getApellido()).isEqualTo("UNIVERSIDAD DE MENDOZA");
        assertThat(factura.getNombre()).isEmpty();
        assertThat(factura.getCuit()).isEqualTo("30518594466");
        assertThat(factura.getCondicionIva()).isEqualTo("Responsable Inscripto");
        assertThat(factura.getImporte()).isEqualByComparingTo("1234.50");
        assertThat(factura.getCae()).isEqualTo("75123456789012");
        assertThat(factura.getFechaVencimientoCae())
                .isEqualTo(OffsetDateTime.of(2026, 10, 31, 0, 0, 0, 0, ZoneOffset.UTC));
        assertThat(factura.getFechaRecibo()).isEqualTo(medianocheUtcDeHoy());
    }

    @Test
    void traeLaCuotaCuandoElPagoNoLaTrae() {
        var pago = pago(false);
        stub(cfe());
        stubAfip(respuesta("A"));
        when(chequeraCuotaService.getByUnique(1, 2, 3L, 4, 5, 6)).thenReturn(cuota());

        assertThat(useCase.facturarCuota(pago)).isTrue();

        verify(chequeraCuotaService).getByUnique(1, 2, 3L, 4, 5, 6);
        assertThat(pago.isTieneCuota()).isTrue();
        assertThat(pago.getChequeraId()).isEqualTo(CHEQUERA_ID);
        assertThat(capturarSolicitud().getDocumento()).isEqualTo("30518594466");
    }

    @Test
    void unComprobanteSinFacturacionElectronicaNoLlamaAAfip() {
        when(comprobanteService.getById(14)).thenReturn(comprobante(0));

        assertThat(useCase.facturarCuota(pago(true))).isFalse();

        verifyNoInteractions(facturacionAfipService);
        verify(facturacionElectronicaService, never()).registrar(any());
    }

    @Test
    void siAfipFallaDevuelveFalseYSinPersistir() {
        stub(cfe());
        when(facturacionAfipService.facturar(any())).thenThrow(new RuntimeException("AFIP no disponible"));

        assertThat(useCase.facturarCuota(pago(true))).isFalse();

        verify(facturacionElectronicaService, never()).registrar(any());
    }

    @Test
    void siElResultadoNoEsADevuelveFalseYSinPersistir() {
        stub(cfe());
        stubAfip(respuesta("R"));

        assertThat(useCase.facturarCuota(pago(true))).isFalse();

        verify(facturacionElectronicaService, never()).registrar(any());
    }

    @Test
    void usaDocumentoCuandoElCuitEstaVacioYCondicionIvaPorDefecto() {
        stub(cfe("", "UNIVERSIDAD DE MENDOZA", ""));
        stubAfip(respuesta("A"));

        useCase.facturarCuota(pago(true));

        var solicitud = capturarSolicitud();
        assertThat(solicitud.getTipoDocumento()).isEqualTo(96);
        assertThat(solicitud.getDocumento()).isEqualTo("4500");

        var factura = capturarFactura();
        assertThat(factura.getTipoDocumento()).isEqualTo("DU");
        assertThat(factura.getApellido()).isEqualTo("Perez");
        assertThat(factura.getNombre()).isEqualTo("Ana");
        assertThat(factura.getCuit()).isEqualTo("4500");
        assertThat(factura.getCondicionIva()).isEqualTo("Consumidor Final");
    }

    @Test
    void usaDocumentoCuandoElCuitEsSoloEspacios() {
        stub(cfe("   ", "UNIVERSIDAD DE MENDOZA", ""));
        stubAfip(respuesta("A"));

        useCase.facturarCuota(pago(true));

        assertThat(capturarSolicitud().getTipoDocumento()).isEqualTo(96);
        assertThat(capturarFactura().getApellido()).isEqualTo("Perez");
    }

    @Test
    void usaDocumentoCuandoNoExisteChequeraFacturacionElectronica() {
        when(comprobanteService.getById(14)).thenReturn(comprobante(1));
        when(chequeraFacturacionElectronicaService.getByChequeraId(CHEQUERA_ID))
                .thenThrow(new RuntimeException("404"));
        stubAfip(respuesta("A"));

        useCase.facturarCuota(pago(true));

        assertThat(capturarSolicitud().getTipoDocumento()).isEqualTo(96);
        assertThat(capturarFactura().getCondicionIva()).isEqualTo("Consumidor Final");
    }

    @Test
    void redondeaTotalYNetoAlCentavoPeroPersisteElImporteOriginal() {
        var pago = pago(true);
        pago.setImporte(new BigDecimal("1234.565"));
        stub(cfe());
        stubAfip(respuesta("A"));

        useCase.facturarCuota(pago);

        var solicitud = capturarSolicitud();
        assertThat(solicitud.getTotal()).isEqualByComparingTo("1234.57");
        assertThat(solicitud.getTotal().scale()).isEqualTo(2);
        assertThat(solicitud.getNeto()).isEqualByComparingTo("1234.57");
        assertThat(solicitud.getNeto().scale()).isEqualTo(2);
        assertThat(capturarFactura().getImporte()).isEqualByComparingTo("1234.565");
    }

    @Test
    void fallaSiElPagoNoTieneChequera() {
        var pago = pago(true);
        pago.setChequeraId(null);

        assertThatThrownBy(() -> useCase.facturarCuota(pago))
                .isInstanceOf(FacturacionException.class);
    }

    private void stub(ChequeraFacturacionElectronica cfe) {
        when(comprobanteService.getById(14)).thenReturn(comprobante(1));
        when(chequeraFacturacionElectronicaService.getByChequeraId(CHEQUERA_ID)).thenReturn(cfe);
    }

    private void stubAfip(RespuestaFacturacionAfip respuesta) {
        when(facturacionAfipService.test()).thenReturn("OK");
        when(facturacionAfipService.facturar(any())).thenReturn(respuesta);
        when(facturacionElectronicaService.registrar(any())).thenAnswer(invocation -> invocation.getArgument(0));
    }

    private ChequeraPago pago(boolean conCuota) {
        var builder = ChequeraPago.builder()
                .chequeraPagoId(PAGO_ID)
                .facultadId(1)
                .tipoChequeraId(2)
                .chequeraSerieId(3L)
                .productoId(4)
                .alternativaId(5)
                .cuotaId(6)
                .importe(new BigDecimal("1234.50"))
                .tieneCuota(conCuota);

        if (conCuota) {
            builder.chequeraId(CHEQUERA_ID)
                    .personaId(new BigDecimal("4500"))
                    .personaApellido("Perez")
                    .personaNombre("Ana");
        }
        return builder.build();
    }

    private ChequeraCuota cuota() {
        return ChequeraCuota.builder()
                .chequeraId(CHEQUERA_ID)
                .personaId(new BigDecimal("4500"))
                .personaApellido("Perez")
                .personaNombre("Ana")
                .build();
    }

    private Comprobante comprobante(int facturacionElectronica) {
        return Comprobante.builder()
                .comprobanteId(14)
                .facturacionElectronica((byte) facturacionElectronica)
                .comprobanteAfipId(11)
                .puntoVenta(4)
                .descripcion("Factura")
                .build();
    }

    private ChequeraFacturacionElectronica cfe() {
        return cfe("30-51859446-6", "UNIVERSIDAD DE MENDOZA", "Responsable Inscripto");
    }

    private ChequeraFacturacionElectronica cfe(String cuit, String razonSocial, String condicionIva) {
        return ChequeraFacturacionElectronica.builder()
                .chequeraId(CHEQUERA_ID)
                .cuit(cuit)
                .razonSocial(razonSocial)
                .condicionIva(condicionIva)
                .build();
    }

    private RespuestaFacturacionAfip respuesta(String resultado) {
        return RespuestaFacturacionAfip.builder()
                .resultado(resultado)
                .cae("75123456789012")
                .vencimientoCae("20261031")
                .numeroComprobante(77L)
                .build();
    }

    private SolicitudFacturacionAfip capturarSolicitud() {
        ArgumentCaptor<SolicitudFacturacionAfip> captor = ArgumentCaptor.forClass(SolicitudFacturacionAfip.class);
        verify(facturacionAfipService).facturar(captor.capture());
        return captor.getValue();
    }

    private FacturacionElectronica capturarFactura() {
        ArgumentCaptor<FacturacionElectronica> captor = ArgumentCaptor.forClass(FacturacionElectronica.class);
        verify(facturacionElectronicaService).registrar(captor.capture());
        return captor.getValue();
    }

    private OffsetDateTime medianocheUtcDeHoy() {
        return OffsetDateTime.now(ZoneOffset.UTC).withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

}
