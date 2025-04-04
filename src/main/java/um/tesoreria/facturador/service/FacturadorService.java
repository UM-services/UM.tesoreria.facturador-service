package um.tesoreria.facturador.service;

import um.tesoreria.facturador.client.tesoreria.afip.FacturacionAfipClient;
import um.tesoreria.facturador.client.tesoreria.core.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import um.tesoreria.facturador.kotlin.tesoreria.core.dto.*;
import um.tesoreria.facturador.kotlin.tesoreria.afip.dto.FacturacionDto;
import um.tesoreria.facturador.model.dto.ReciboMessageDto;
import um.tesoreria.facturador.service.queue.ReciboQueueService;

import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class FacturadorService {

    private final ComprobanteClient comprobanteClient;
    private final ChequeraFacturacionElectronicaClient chequeraFacturacionElectronicaClient;
    private final FacturacionElectronicaClient facturacionElectronicaClient;
    private final ChequeraPagoClient chequeraPagoClient;
    private final ChequeraCuotaClient chequeraCuotaClient;
    private final FacturacionAfipClient facturacionAfipClient;
    private final ReciboQueueService reciboQueueService;

    public FacturadorService(ComprobanteClient comprobanteClient,
                             ChequeraFacturacionElectronicaClient chequeraFacturacionElectronicaClient,
                             FacturacionElectronicaClient facturacionElectronicaClient,
                             ChequeraPagoClient chequeraPagoClient,
                             ChequeraCuotaClient chequeraCuotaClient,
                             FacturacionAfipClient facturacionAfipClient,
                             ReciboQueueService reciboQueueService) {
        this.comprobanteClient = comprobanteClient;
        this.chequeraFacturacionElectronicaClient = chequeraFacturacionElectronicaClient;
        this.facturacionElectronicaClient = facturacionElectronicaClient;
        this.chequeraPagoClient = chequeraPagoClient;
        this.chequeraCuotaClient = chequeraCuotaClient;
        this.facturacionAfipClient = facturacionAfipClient;
        this.reciboQueueService = reciboQueueService;
    }

    public String facturaPendientes() {
        log.debug("Processing FacturadorService.facturaPendientes");
        OffsetDateTime now = OffsetDateTime.now().with(LocalTime.MIDNIGHT);
        OffsetDateTime startDate = now.minusDays(60);
        OffsetDateTime endDate = now.plusDays(2);
        for (OffsetDateTime fechaPago = startDate; fechaPago.isBefore(endDate); fechaPago = fechaPago.plusDays(1)) {
            log.info("Procesando Fecha de Pago: {}", fechaPago);
            for (ChequeraPagoDto chequeraPago : chequeraPagoClient.pendientesFactura(fechaPago)) {
                logChequeraPago(chequeraPago);
                if (facturaCuota(chequeraPago)) {
                    log.info("Facturado Ok");
                } else {
                    log.info("Facturado NO");
                }
            }
        }
        return "Fin proceso";
    }

    public String facturaOne(Long chequeraPagoId) {
        log.debug("Processing FacturadorService.facturaOne");
        try {
            var facturacionElectronica = facturacionElectronicaClient.findByChequeraPagoId(chequeraPagoId);
            logFacturacionElectronica(facturacionElectronica);
            return "ERROR: Facturación previa";
        } catch (Exception e) {
            log.debug("Facturacion pendiente");
        }
        var chequeraPago = chequeraPagoClient.findByChequeraPagoId(chequeraPagoId);
        logChequeraPago(chequeraPago);
        if (facturaCuota(chequeraPago)) {
            log.info("Facturado Ok");
            return "Facturado Ok";
        }
        return "Facturado NO";
    }

    public void sendRecibosPendientes() {
        log.debug("Processing FacturadorService.sendPendientes");
        for (var facturacionElectronica : facturacionElectronicaClient.find100Pendientes()) {
            logFacturacionElectronica(facturacionElectronica);
            var chequeraPago = chequeraPagoClient.findByChequeraPagoId(facturacionElectronica.getChequeraPagoId());
            logChequeraPago(chequeraPago);
            log.debug("FacturadorService.sendPendientes.enviandoRecibo");
            reciboQueueService.sendReciboQueue(ReciboMessageDto
                    .builder()
                    .uuid(UUID.randomUUID())
                    .facturacionElectronicaId(facturacionElectronica.getFacturacionElectronicaId())
                    .chequeraPagoId(chequeraPago.getChequeraPagoId())
                    .facultadId(chequeraPago.getFacultadId())
                    .tipoChequeraId(chequeraPago.getTipoChequeraId())
                    .chequeraSerieId(chequeraPago.getChequeraSerieId())
                    .productoId(chequeraPago.getProductoId())
                    .alternativaId(chequeraPago.getAlternativaId())
                    .cuotaId(chequeraPago.getCuotaId())
                    .build());
        }
    }

    public String sendOneByChequeraPagoId(Long chequeraPagoId) {
        log.debug("Processing FacturadorService.sendOneByChequeraPagoId");
        FacturacionElectronicaDto facturacionElectronica;
        ChequeraPagoDto chequeraPago;
        try {
            facturacionElectronica = facturacionElectronicaClient.findByChequeraPagoId(chequeraPagoId);
            chequeraPago = chequeraPagoClient.findByChequeraPagoId(chequeraPagoId);
        } catch (Exception e) {
            return "Facturación pendiente";
        }
        logFacturacionElectronica(facturacionElectronica);
        log.debug("FacturadorService.sendOneByChequeraPagoId.enviandoRecibo");
        reciboQueueService.sendReciboQueue(ReciboMessageDto
                .builder()
                .uuid(UUID.randomUUID())
                .facturacionElectronicaId(facturacionElectronica.getFacturacionElectronicaId())
                .chequeraPagoId(chequeraPago.getChequeraPagoId())
                .facultadId(chequeraPago.getFacultadId())
                .tipoChequeraId(chequeraPago.getTipoChequeraId())
                .chequeraSerieId(chequeraPago.getChequeraSerieId())
                .productoId(chequeraPago.getProductoId())
                .alternativaId(chequeraPago.getAlternativaId())
                .cuotaId(chequeraPago.getCuotaId())
                .build());
        return "Envío solicitado";
    }

    public String sendOneByFacturacionElectronicaId(Long facturacionElectronicaId) {
        log.debug("Processing FacturadorService.sendOneByFacturacionElectronicaId");
        FacturacionElectronicaDto facturacionElectronica;
        ChequeraPagoDto chequeraPago;
        try {
            facturacionElectronica = facturacionElectronicaClient.findByFacturacionElectronicaId(facturacionElectronicaId);
            logFacturacionElectronica(facturacionElectronica);
            chequeraPago = chequeraPagoClient.findByChequeraPagoId(facturacionElectronica.getChequeraPagoId());
            logChequeraPago(chequeraPago);
        } catch (Exception e) {
            return "Facturación pendiente";
        }
        log.debug("FacturadorService.sendOneByFacturacionElectronicaId.enviandoRecibo");
        var message = ReciboMessageDto
                .builder()
                .uuid(UUID.randomUUID())
                .facturacionElectronicaId(facturacionElectronica.getFacturacionElectronicaId())
                .chequeraPagoId(chequeraPago.getChequeraPagoId())
                .facultadId(chequeraPago.getFacultadId())
                .tipoChequeraId(chequeraPago.getTipoChequeraId())
                .chequeraSerieId(chequeraPago.getChequeraSerieId())
                .productoId(chequeraPago.getProductoId())
                .alternativaId(chequeraPago.getAlternativaId())
                .cuotaId(chequeraPago.getCuotaId())
                .build();
        logReciboMessage(message);
        reciboQueueService.sendReciboQueue(message);
        return "Envío de Recibo Solicitado";
    }

    public boolean facturaCuota(ChequeraPagoDto chequeraPago) {
        log.debug("Processing FacturadorService.facturaCuota");

        // empresaCuit = "30-51859446-6";
        // empresaRazonSocial = "UNIVERSIDAD DE MENDOZA";

        // verifica la lectura de la cuota
        if (chequeraPago.getChequeraCuota() == null) {
            chequeraPago.setChequeraCuota(chequeraCuotaClient.findByUnique(chequeraPago.getFacultadId(), chequeraPago.getTipoChequeraId(), chequeraPago.getChequeraSerieId(), chequeraPago.getProductoId(), chequeraPago.getAlternativaId(), chequeraPago.getCuotaId()));
        }
        logChequeraPago(chequeraPago);

        // proceso
        ChequeraSerieDto chequeraSerie = chequeraPago.getChequeraCuota().getChequeraSerie();
        assert chequeraSerie != null;
        PersonaDto persona = chequeraSerie.getPersona();
        ComprobanteDto comprobante = comprobanteClient.findByComprobanteId(14);

        logComprobante(comprobante);

        if (comprobante.getFacturacionElectronica() == 0) {
            return false;
        }

        ChequeraFacturacionElectronicaDto chequeraFacturacionElectronica;
        try {
            chequeraFacturacionElectronica = chequeraFacturacionElectronicaClient.findByChequeraId(chequeraSerie.getChequeraId());
            logChequeraFacturacionElectronica(chequeraFacturacionElectronica);
        } catch (Exception e) {
            chequeraFacturacionElectronica = new ChequeraFacturacionElectronicaDto();
        }

        var tipoAfip = comprobante.getComprobanteAfipId();
        var puntoVenta = comprobante.getPuntoVenta();
        var importePagado = chequeraPago.getImporte();

        var tipoDocumentoAfip = 80;
        var tipoDocumento = "CUIT";
        var apellido = chequeraFacturacionElectronica.getRazonSocial();
        var nombre = "";
        var numeroDocumento = chequeraFacturacionElectronica.getCuit().trim().replace("-", "");

        if (numeroDocumento.isEmpty()) {
            tipoDocumento = "DU";
            tipoDocumentoAfip = 96;
            assert persona != null;
            apellido = persona.getApellido();
            nombre = persona.getNombre();
            numeroDocumento = String.valueOf(persona.getPersonaId()).trim();
        }

        assert tipoAfip != null;
        assert puntoVenta != null;
        FacturacionDto facturacion = new FacturacionDto.Builder()
                .tipoDocumento(tipoDocumentoAfip)
                .documento(numeroDocumento)
                .tipoAfip(tipoAfip)
                .puntoVenta(puntoVenta)
                .total(importePagado.setScale(2, RoundingMode.HALF_UP))
                .neto(importePagado.setScale(2, RoundingMode.HALF_UP))
                .build();

        log.info("Afip Test -> {}", facturacionAfipClient.test());
        logFacturacion(apellido, nombre, facturacion);

        try {
            facturacion = facturacionAfipClient.facturador(facturacion);
            logFacturacion(apellido, nombre, facturacion);
        } catch (Exception e) {
            log.debug("Servicio de Facturación NO disponible");
            return false;
        }

        if (facturacion.getResultado().equals("A")) {
            // Convierte fechas
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate localDate = LocalDate.parse(facturacion.getVencimientoCae(), formatter);
            OffsetDateTime fechaVencimientoCae = OffsetDateTime.of(localDate, LocalTime.MIDNIGHT, ZoneOffset.UTC);
            // Registra el resultado de la AFIP
            assert persona != null;
            FacturacionElectronicaDto facturacionElectronica = new FacturacionElectronicaDto.Builder()
                    .chequeraPagoId(chequeraPago.getChequeraPagoId())
                    .comprobanteId(comprobante.getComprobanteId())
                    .numeroComprobante(facturacion.getNumeroComprobante())
                    .personaId(persona.getPersonaId())
                    .tipoDocumento(tipoDocumento)
                    .apellido(apellido)
                    .nombre(nombre)
                    .cuit(numeroDocumento)
                    .condicionIva(chequeraFacturacionElectronica.getCondicionIva().isEmpty() ? "Consumidor Final" : chequeraFacturacionElectronica.getCondicionIva())
                    .importe(chequeraPago.getImporte())
                    .cae(facturacion.getCae())
                    .fechaRecibo(OffsetDateTime.now(ZoneOffset.UTC).withHour(0).withMinute(0).withSecond(0).withNano(0))
                    .fechaVencimientoCae(fechaVencimientoCae)
                    .build();
            log.debug("before add FacturacionElectronica");
            logFacturacionElectronica(facturacionElectronica);
            facturacionElectronica = facturacionElectronicaClient.add(facturacionElectronica);
            log.debug("after add FacturacionElectronica");
            logFacturacionElectronica(facturacionElectronica);
            return true;
        }
        return false;
    }

    public void testInvoiceQueue(Long facturaElectronicaId) {
        log.debug("Processing FacturadorService.testInvoiceQueue");
        var facturacionElectronica = facturacionElectronicaClient.findByFacturacionElectronicaId(facturaElectronicaId);
        var chequeraPago = chequeraPagoClient.findByChequeraPagoId(facturacionElectronica.getChequeraPagoId());
        logFacturacionElectronica(facturacionElectronica);
        reciboQueueService.sendReciboQueue(ReciboMessageDto
                .builder()
                .uuid(UUID.randomUUID())
                .facturacionElectronicaId(facturacionElectronica.getFacturacionElectronicaId())
                .chequeraPagoId(chequeraPago.getChequeraPagoId())
                .facultadId(chequeraPago.getFacultadId())
                .tipoChequeraId(chequeraPago.getTipoChequeraId())
                .chequeraSerieId(chequeraPago.getChequeraSerieId())
                .productoId(chequeraPago.getProductoId())
                .alternativaId(chequeraPago.getAlternativaId())
                .cuotaId(chequeraPago.getCuotaId())
                .build());
    }

    public void testManyInvoiceQueue() {
        log.debug("Processing FacturadorService.testManyInvoiceQueue");
        List<Long> facturacionElectronicaIds = Arrays.asList(97305L, 135467L, 145274L, 160173L, 173471L, 191979L, 203757L, 215882L, 222657L, 266982L, 266984L, 266985L, 266987L);
        for (var facturacionElectronicaId : facturacionElectronicaIds) {
            var facturacionElectronica = facturacionElectronicaClient.findByFacturacionElectronicaId(facturacionElectronicaId);
            var chequeraPago = chequeraPagoClient.findByChequeraPagoId(facturacionElectronica.getChequeraPagoId());
            logFacturacionElectronica(facturacionElectronica);
            reciboQueueService.sendReciboQueue(ReciboMessageDto
                    .builder()
                    .uuid(UUID.randomUUID())
                    .facturacionElectronicaId(facturacionElectronica.getFacturacionElectronicaId())
                    .chequeraPagoId(chequeraPago.getChequeraPagoId())
                    .facultadId(chequeraPago.getFacultadId())
                    .tipoChequeraId(chequeraPago.getTipoChequeraId())
                    .chequeraSerieId(chequeraPago.getChequeraSerieId())
                    .productoId(chequeraPago.getProductoId())
                    .alternativaId(chequeraPago.getAlternativaId())
                    .cuotaId(chequeraPago.getCuotaId())
                    .build());
        }
    }

    private void logFacturacion(String apellido, String nombre, FacturacionDto facturacion) {
        try {
            log.info("Facturacion {} {}: {}", apellido, nombre, JsonMapper
                    .builder()
                    .findAndAddModules()
                    .build()
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(facturacion));
        } catch (JsonProcessingException e) {
            log.info("Facturacion jsonify error {} {}: {}", apellido, nombre, e.getMessage());
        }
    }

    private void logChequeraFacturacionElectronica(ChequeraFacturacionElectronicaDto chequeraFacturacionElectronica) {
        try {
            log.debug("ChequeraFacturacionElectronica={}", JsonMapper
                    .builder()
                    .findAndAddModules()
                    .build()
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(chequeraFacturacionElectronica));
        } catch (JsonProcessingException e) {
            log.debug("ChequeraFacturacionElectronica jsonify error: {}", e.getMessage());
        }
    }

    private void logComprobante(ComprobanteDto comprobante) {
        try {
            log.debug("Comprobante={}", JsonMapper
                    .builder()
                    .findAndAddModules()
                    .build()
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(comprobante));
        } catch (JsonProcessingException e) {
            log.debug("Comprobante jsonify error: {}", e.getMessage());
        }
    }

    private void logChequeraPago(ChequeraPagoDto chequeraPago) {
        try {
            log.debug("ChequeraPago: {}", JsonMapper
                    .builder()
                    .findAndAddModules()
                    .build()
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(chequeraPago));
        } catch (JsonProcessingException e) {
            log.debug("ChequeraPago jsonify error: {}", e.getMessage());
        }
    }

    private void logFacturacionElectronica(FacturacionElectronicaDto facturacionElectronica) {
        try {
            log.debug("Facturación Electronica: {}", JsonMapper
                    .builder()
                    .findAndAddModules()
                    .build()
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(facturacionElectronica));
        } catch (JsonProcessingException e) {
            log.debug("Facturación Electronica jsonify error: {}", e.getMessage());
        }
    }

    private void logReciboMessage(ReciboMessageDto message) {
        try {
            log.debug("ReciboMessage: {}", JsonMapper
                    .builder()
                    .findAndAddModules()
                    .build()
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(message));
        } catch (JsonProcessingException e) {
            log.debug("ReciboMessage jsonify error: {}", e.getMessage());
        }
    }

}
