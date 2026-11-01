package um.tesoreria.facturador.hexagonal.facturacion.procesos.emision.application.usecases;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraCuota.application.service.ChequeraCuotaService;
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
import um.tesoreria.facturador.hexagonal.facturacion.procesos.emision.domain.ports.in.FacturarCuotaUseCase;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/*
 * Excepcion autorizada (M1, patron tesoreria-guarani): este slice de proceso compone
 * slices de datos y gateways importando sus modelos de dominio publicos y sus servicios
 * de aplicacion. No accede a repositorios, adaptadores ni entidades de infraestructura.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class FacturarCuotaUseCaseImpl implements FacturarCuotaUseCase {

    private static final Integer COMPROBANTE_FACTURA = 14;
    private static final int TIPO_DOCUMENTO_CUIT = 80;
    private static final int TIPO_DOCUMENTO_DU = 96;
    private static final int ID_CONDICION_IVA = 5;
    private static final String CONDICION_IVA_DEFAULT = "Consumidor Final";
    private static final DateTimeFormatter FORMATO_VENCIMIENTO_CAE = DateTimeFormatter.ofPattern("yyyyMMdd");

    private final ChequeraCuotaService chequeraCuotaService;
    private final ComprobanteService comprobanteService;
    private final ChequeraFacturacionElectronicaService chequeraFacturacionElectronicaService;
    private final FacturacionElectronicaService facturacionElectronicaService;
    private final FacturacionAfipService facturacionAfipService;

    @Override
    public boolean facturarCuota(ChequeraPago chequeraPago) {
        log.debug("Processing FacturarCuotaUseCaseImpl.facturarCuota");
        if (chequeraPago == null) {
            throw new FacturacionException();
        }

        if (!chequeraPago.isTieneCuota()) {
            var cuota = chequeraCuotaService.getByUnique(chequeraPago.getFacultadId(),
                    chequeraPago.getTipoChequeraId(), chequeraPago.getChequeraSerieId(),
                    chequeraPago.getProductoId(), chequeraPago.getAlternativaId(), chequeraPago.getCuotaId());
            chequeraPago.setChequeraId(cuota.getChequeraId());
            chequeraPago.setPersonaId(cuota.getPersonaId());
            chequeraPago.setPersonaApellido(cuota.getPersonaApellido());
            chequeraPago.setPersonaNombre(cuota.getPersonaNombre());
            chequeraPago.setTieneCuota(true);
        }
        log.debug("ChequeraPago -> {}", chequeraPago);

        if (chequeraPago.getChequeraId() == null) {
            throw new FacturacionException(chequeraPago.getChequeraPagoId());
        }

        Comprobante comprobante = comprobanteService.getById(COMPROBANTE_FACTURA);
        log.debug("Comprobante -> {}", comprobante);

        if (comprobante.getFacturacionElectronica() == 0) {
            return false;
        }

        ChequeraFacturacionElectronica chequeraFacturacionElectronica;
        try {
            chequeraFacturacionElectronica = chequeraFacturacionElectronicaService.getByChequeraId(chequeraPago.getChequeraId());
            log.debug("ChequeraFacturacionElectronica -> {}", chequeraFacturacionElectronica);
        } catch (Exception e) {
            chequeraFacturacionElectronica = new ChequeraFacturacionElectronica();
        }

        Integer tipoAfip = comprobante.getComprobanteAfipId();
        Integer puntoVenta = comprobante.getPuntoVenta();
        BigDecimal importePagado = chequeraPago.getImporte();

        int tipoDocumentoAfip = TIPO_DOCUMENTO_CUIT;
        String tipoDocumento = "CUIT";
        String apellido = chequeraFacturacionElectronica.getRazonSocial();
        String nombre = "";
        String numeroDocumento = chequeraFacturacionElectronica.getCuit().trim().replace("-", "");

        if (numeroDocumento.isEmpty()) {
            tipoDocumento = "DU";
            tipoDocumentoAfip = TIPO_DOCUMENTO_DU;
            apellido = chequeraPago.getPersonaApellido();
            nombre = chequeraPago.getPersonaNombre();
            numeroDocumento = String.valueOf(chequeraPago.getPersonaId()).trim();
        }

        if (tipoAfip == null || puntoVenta == null || importePagado == null) {
            throw new FacturacionException(chequeraPago.getChequeraPagoId());
        }

        BigDecimal importeRedondeado = importePagado.setScale(2, RoundingMode.HALF_UP);
        SolicitudFacturacionAfip solicitud = SolicitudFacturacionAfip.builder()
                .tipoDocumento(tipoDocumentoAfip)
                .documento(numeroDocumento)
                .tipoAfip(tipoAfip)
                .puntoVenta(puntoVenta)
                .total(importeRedondeado)
                .neto(importeRedondeado)
                .idCondicionIva(ID_CONDICION_IVA)
                .build();

        log.info("Afip Test -> {}", facturacionAfipService.test());
        log.debug("Facturacion -> {}", solicitud);

        RespuestaFacturacionAfip facturacion;
        try {
            facturacion = facturacionAfipService.facturar(solicitud);
            log.debug("Facturacion -> {}", facturacion);
        } catch (Exception e) {
            log.debug("Servicio de Facturación NO disponible");
            return false;
        }

        if (facturacion.getResultado().equals("A")) {
            LocalDate localDate = LocalDate.parse(facturacion.getVencimientoCae(), FORMATO_VENCIMIENTO_CAE);
            OffsetDateTime fechaVencimientoCae = OffsetDateTime.of(localDate, LocalTime.MIDNIGHT, ZoneOffset.UTC);

            FacturacionElectronica facturacionElectronica = FacturacionElectronica.builder()
                    .chequeraPagoId(chequeraPago.getChequeraPagoId())
                    .comprobanteId(comprobante.getComprobanteId())
                    .numeroComprobante(facturacion.getNumeroComprobante())
                    .personaId(chequeraPago.getPersonaId())
                    .tipoDocumento(tipoDocumento)
                    .apellido(apellido)
                    .nombre(nombre)
                    .cuit(numeroDocumento)
                    .condicionIva(chequeraFacturacionElectronica.getCondicionIva().isEmpty()
                            ? CONDICION_IVA_DEFAULT
                            : chequeraFacturacionElectronica.getCondicionIva())
                    .importe(chequeraPago.getImporte())
                    .cae(facturacion.getCae())
                    .fechaRecibo(OffsetDateTime.now(ZoneOffset.UTC)
                            .withHour(0).withMinute(0).withSecond(0).withNano(0))
                    .fechaVencimientoCae(fechaVencimientoCae)
                    .build();
            facturacionElectronicaService.registrar(facturacionElectronica);
            return true;
        }
        return false;
    }

}
