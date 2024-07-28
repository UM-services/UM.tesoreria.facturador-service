package um.tesoreria.facturador.service;

import um.tesoreria.facturador.client.tesoreria.afip.FacturacionAfipClient;
import um.tesoreria.facturador.client.tesoreria.core.ChequeraFacturacionElectronicaClient;
import um.tesoreria.facturador.client.tesoreria.core.ChequeraPagoClient;
import um.tesoreria.facturador.client.tesoreria.core.ComprobanteClient;
import um.tesoreria.facturador.client.tesoreria.core.FacturacionElectronicaClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
    private final FacturacionAfipClient facturacionAfipClient;

    public FacturadorService(ComprobanteClient comprobanteClient, ChequeraFacturacionElectronicaClient chequeraFacturacionElectronicaClient,
                             FacturacionElectronicaClient facturacionElectronicaClient, ChequeraPagoClient chequeraPagoClient,
                             FacturacionAfipClient facturacionAfipClient) {
        this.comprobanteClient = comprobanteClient;
        this.chequeraFacturacionElectronicaClient = chequeraFacturacionElectronicaClient;
        this.facturacionElectronicaClient = facturacionElectronicaClient;
        this.chequeraPagoClient = chequeraPagoClient;
        this.facturacionAfipClient = facturacionAfipClient;
    }

    public String facturaPendientes() {
        OffsetDateTime now = OffsetDateTime.now().with(LocalTime.MIDNIGHT);
        OffsetDateTime startDate = now.minusDays(60);
        OffsetDateTime endDate = now.plusDays(2);
        for (OffsetDateTime fechaPago = startDate; fechaPago.isBefore(endDate); fechaPago = fechaPago.plusDays(1)) {
            log.debug("Procesando Fecha de Pago: {}", fechaPago);
            for (ChequeraPagoDto chequeraPago : chequeraPagoClient.pendientesFactura(fechaPago)) {
                try {
                    log.debug("Procesando ChequeraPago: {}", JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(chequeraPago));
                } catch (JsonProcessingException e) {
                    log.debug("Procesando ChequeraPago: {}", e.getMessage());
                }
                if (facturaCuota(chequeraPago)) {
                    log.debug("Facturado Ok");
                } else {
                    log.debug("Facturado NO");
                }
            }
        }
        return "Ok";
    }

    public String facturaOne(Long chequeraPagoId) {
        try {
            var facturacionElectronica = facturacionElectronicaClient.findByChequeraPagoId(chequeraPagoId);
            try {
                log.debug("Facturado Electronica: {}", JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(facturacionElectronica));
            } catch (JsonProcessingException e) {
                log.debug("Facturado Electronica: problema JSON {}", e.getMessage());
            }
            return "ERROR: Facturación previa";
        } catch (Exception e) {
            log.debug("Facturacion pendiente");
        }
        var chequeraPago = chequeraPagoClient.findByChequeraPagoId(chequeraPagoId);
        try {
            log.debug("ChequeraPago: {}", JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(chequeraPago));
        } catch (JsonProcessingException e) {
            log.debug("ChequeraPago: problema JSON {}", e.getMessage());
        }
        if (facturaCuota(chequeraPago)) {
            return "Facturado Ok";
        }
        return "Facturado NO";
    }

    public boolean facturaCuota(ChequeraPagoDto chequeraPago) {

        var empresaCuit = "30-51859446-6";
        var empresaRazonSocial = "UNIVERSIDAD DE MENDOZA";

        ChequeraSerieDto chequeraSerie = chequeraPago.getChequeraCuota().getChequeraSerie();
        PersonaDto persona = chequeraSerie.getPersona();
        ComprobanteDto comprobante = comprobanteClient.findByComprobanteId(14);

        try {
            log.debug("Comprobante={}", JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(comprobante));
        } catch (JsonProcessingException e) {
            log.debug("Comprobante=null");
        }

        if (comprobante.getFacturacionElectronica() == 0) {
            return false;
        }

        ChequeraFacturacionElectronicaDto chequeraFacturacionElectronica = null;
        try {
            chequeraFacturacionElectronica = chequeraFacturacionElectronicaClient.findByChequeraId(chequeraSerie.getChequeraId());
            log.debug("ChequeraFacturacionElectronica={}", JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(chequeraFacturacionElectronica));
        } catch (JsonProcessingException e) {
            log.debug("ChequeraFacturacionElectronica={}", e.getMessage());
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
        try {
            log.info("Facturacion {} {}: {}", apellido, nombre, JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(facturacion));
        } catch (JsonProcessingException e) {
            log.info("Facturacion {} {}: null", apellido, nombre);
        }

        try {
            facturacion = facturacionAfipClient.facturador(facturacion);
            try {
                log.info("Facturacion (after) {} {}: {}", apellido, nombre, JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(facturacion));
            } catch (JsonProcessingException e) {
                log.info("Facturacion (after) {} {}: null", apellido, nombre);
            }
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
            try {
                log.info("Registro de AFIP: {}", JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(facturacionElectronica));
            } catch (JsonProcessingException e) {
                log.info("Registro de AFIP: {}", e.getMessage());
            }
            facturacionElectronica = facturacionElectronicaClient.add(facturacionElectronica);
            try {
                log.info("Registro de AFIP (after): {}", JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(facturacionElectronica));
            } catch (JsonProcessingException e) {
                log.info("Registro de AFIP (after): {}", e.getMessage());
            }
            return true;
        }

        return false;

    }

}
