package um.tesoreria.facturador.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import um.tesoreria.facturador.client.tesoreria.afip.FacturacionAfipClient;
import um.tesoreria.facturador.client.tesoreria.core.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import um.tesoreria.facturador.configuration.RabbitMQConfig;
import um.tesoreria.facturador.kotlin.tesoreria.core.dto.*;
import um.tesoreria.facturador.kotlin.tesoreria.afip.dto.FacturacionDto;

import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class FacturadorService {

    private final ComprobanteClient comprobanteClient;
    private final ChequeraFacturacionElectronicaClient chequeraFacturacionElectronicaClient;
    private final FacturacionElectronicaClient facturacionElectronicaClient;
    private final ChequeraPagoClient chequeraPagoClient;
    private final ChequeraCuotaClient chequeraCuotaClient;
    private final FacturacionAfipClient facturacionAfipClient;
    private final RabbitTemplate rabbitTemplate;

    public FacturadorService(ComprobanteClient comprobanteClient,
                             ChequeraFacturacionElectronicaClient chequeraFacturacionElectronicaClient,
                             FacturacionElectronicaClient facturacionElectronicaClient,
                             ChequeraPagoClient chequeraPagoClient,
                             ChequeraCuotaClient chequeraCuotaClient,
                             FacturacionAfipClient facturacionAfipClient,
                             RabbitTemplate rabbitTemplate) {
        this.comprobanteClient = comprobanteClient;
        this.chequeraFacturacionElectronicaClient = chequeraFacturacionElectronicaClient;
        this.facturacionElectronicaClient = facturacionElectronicaClient;
        this.chequeraPagoClient = chequeraPagoClient;
        this.chequeraCuotaClient = chequeraCuotaClient;
        this.facturacionAfipClient = facturacionAfipClient;
        this.rabbitTemplate = rabbitTemplate;
    }

    public String facturaPendientes() {
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

    public boolean facturaCuota(ChequeraPagoDto chequeraPago) {

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

        ChequeraFacturacionElectronicaDto chequeraFacturacionElectronica = null;
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
            log.debug("before");
            logFacturacionElectronica(facturacionElectronica);
            facturacionElectronica = facturacionElectronicaClient.add(facturacionElectronica);
            log.debug("encolando envío");
            sendReciboQueue(facturacionElectronica);
            log.debug("after");
            logFacturacionElectronica(facturacionElectronica);
            return true;
        }
        return false;
    }

    private void sendReciboQueue(FacturacionElectronicaDto facturacionElectronica) {
        log.debug("Processing sendReciboQueue");
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_INVOICE, facturacionElectronica);
    }

    public void testInvoiceQueue(Long facturaElectronicaId) {
        log.debug("Processing testInvoiceQueue");
        var facturacionElectronica = facturacionElectronicaClient.findByFacturacionElectronicaId(facturaElectronicaId);
        logFacturacionElectronica(facturacionElectronica);
        sendReciboQueue(facturacionElectronica);
    }

    private void logFacturacion(String apellido, String nombre, FacturacionDto facturacion) {
        try {
            log.info("Facturacion {} {}: {}", apellido, nombre, JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(facturacion));
        } catch (JsonProcessingException e) {
            log.info("Facturacion jsonify error {} {}: {}", apellido, nombre, e.getMessage());
        }
    }

    private void logChequeraFacturacionElectronica(ChequeraFacturacionElectronicaDto chequeraFacturacionElectronica) {
        try {
            log.debug("ChequeraFacturacionElectronica={}", JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(chequeraFacturacionElectronica));
        } catch (JsonProcessingException e) {
            log.debug("ChequeraFacturacionElectronica jsonify error: {}", e.getMessage());
        }
    }

    private void logComprobante(ComprobanteDto comprobante) {
        try {
            log.debug("Comprobante={}", JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(comprobante));
        } catch (JsonProcessingException e) {
            log.debug("Comprobante jsonify error: {}", e.getMessage());
        }
    }

    private void logChequeraPago(ChequeraPagoDto chequeraPago) {
        try {
            log.debug("ChequeraPago: {}", JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(chequeraPago));
        } catch (JsonProcessingException e) {
            log.debug("ChequeraPago jsonify error: {}", e.getMessage());
        }
    }

    private void logFacturacionElectronica(FacturacionElectronicaDto facturacionElectronica) {
        try {
            log.debug("Facturación Electronica: {}", JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(facturacionElectronica));
        } catch (JsonProcessingException e) {
            log.debug("Facturación Electronica jsonify error: {}", e.getMessage());
        }
    }

}
