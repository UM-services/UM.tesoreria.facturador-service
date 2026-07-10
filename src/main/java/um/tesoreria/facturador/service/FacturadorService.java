package um.tesoreria.facturador.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import um.tesoreria.facturador.client.tesoreria.afip.FacturacionAfipClient;
import um.tesoreria.facturador.client.tesoreria.core.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import um.tesoreria.facturador.model.dto.*;
import um.tesoreria.facturador.model.dto.afip.FacturacionDto;

import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
@RequiredArgsConstructor
public class FacturadorService {

    private final ComprobanteClient comprobanteClient;
    private final ChequeraFacturacionElectronicaClient chequeraFacturacionElectronicaClient;
    private final FacturacionElectronicaClient facturacionElectronicaClient;
    private final ChequeraPagoClient chequeraPagoClient;
    private final ChequeraCuotaClient chequeraCuotaClient;
    private final FacturacionAfipClient facturacionAfipClient;
    private final ReciboClient reciboClient;

    public String facturaPendientes() {
        log.debug("Processing FacturadorService.facturaPendientes");
        OffsetDateTime now = OffsetDateTime.now().with(LocalTime.MIDNIGHT);
        OffsetDateTime startDate = now.minusDays(90);
        OffsetDateTime endDate = now.plusDays(2);
        for (OffsetDateTime fechaPago = startDate; fechaPago.isBefore(endDate); fechaPago = fechaPago.plusDays(1)) {
            log.info("Procesando Fecha de Pago: {}", fechaPago);
            for (ChequeraPagoDto chequeraPago : chequeraPagoClient.pendientesFactura(fechaPago)) {
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
            FacturacionElectronicaDto facturacionElectronica = facturacionElectronicaClient.findByChequeraPagoId(chequeraPagoId);
            log.debug("FacturacionElectronica -> {}", facturacionElectronica.jsonify());
            return "ERROR: Facturación previa";
        } catch (Exception e) {
            log.debug("Facturacion pendiente");
        }
        ChequeraPagoDto chequeraPago = chequeraPagoClient.findByChequeraPagoId(chequeraPagoId);
        log.debug("ChequeraPago -> {}", chequeraPago.jsonify());
        if (facturaCuota(chequeraPago)) {
            log.info("Facturado Ok");
            return "Facturado Ok";
        }
        return "Facturado NO";
    }

    public String sendOneByChequeraPagoId(Long chequeraPagoId) {
        log.debug("\n\nProcessing FacturadorService.sendOneByChequeraPagoId\n\n");
        FacturacionElectronicaDto facturacionElectronica;
        try {
            facturacionElectronica = facturacionElectronicaClient.findByChequeraPagoId(chequeraPagoId);
            chequeraPagoClient.findByChequeraPagoId(chequeraPagoId);
        } catch (Exception e) {
            log.error("Facturacion pendiente");
            return "Facturación pendiente";
        }
        log.debug("FacturacionElectronica -> {}", facturacionElectronica.jsonify());
        log.debug("\n\nFacturadorService.sendOneByChequeraPagoId.enviandoRecibo\n\n");
        return reciboClient.send(facturacionElectronica.getFacturacionElectronicaId());
    }

    public String sendOneByFacturacionElectronicaId(Long facturacionElectronicaId) {
        log.debug("Processing FacturadorService.sendOneByFacturacionElectronicaId");
        FacturacionElectronicaDto facturacionElectronica;
        ChequeraPagoDto chequeraPago;
        try {
            facturacionElectronica = facturacionElectronicaClient.findByFacturacionElectronicaId(facturacionElectronicaId);
            log.debug("FacturacionElectronica -> {}", facturacionElectronica.jsonify());
            chequeraPago = chequeraPagoClient.findByChequeraPagoId(facturacionElectronica.getChequeraPagoId());
            log.debug("ChequeraPago -> {}", chequeraPago.jsonify());
        } catch (Exception e) {
            log.debug("\n\nError enviando recibo -> {}\n\n", e.getMessage());
            return "Facturación pendiente";
        }
        log.debug("FacturadorService.sendOneByFacturacionElectronicaId.enviandoRecibo");
        try {
            return reciboClient.send(facturacionElectronicaId);
        } catch (FeignException.BadRequest e) {
            log.error("No se pudo enviar el recibo {}. Motivo: {}", facturacionElectronicaId, e.getMessage());
            return "Error de envío";
        }
    }

    public void sendFacturasPendientes() {
        log.info("Processing FacturadorService.sendFacturasPendientes");
        for (FacturacionElectronicaDto facturacionElectronica : facturacionElectronicaClient.find3Pendientes()) {
            log.info("Sending from FacturadorService.sendFacturasPendientes");
            log.debug("FacturacionElectronica -> {}", facturacionElectronica.jsonify());
            sendOneByFacturacionElectronicaId(facturacionElectronica.getFacturacionElectronicaId());
        }
    }

    public boolean facturaCuota(ChequeraPagoDto chequeraPago) {
        log.debug("Processing FacturadorService.facturaCuota");

        // empresaCuit = "30-51859446-6";
        // empresaRazonSocial = "UNIVERSIDAD DE MENDOZA";

        // verifica la lectura de la cuota
        if (chequeraPago.getChequeraCuota() == null) {
            chequeraPago.setChequeraCuota(chequeraCuotaClient.findByUnique(chequeraPago.getFacultadId(), chequeraPago.getTipoChequeraId(), chequeraPago.getChequeraSerieId(), chequeraPago.getProductoId(), chequeraPago.getAlternativaId(), chequeraPago.getCuotaId()));
        }
        log.debug("ChequeraPago -> {}", chequeraPago.jsonify());

        // proceso
        ChequeraSerieDto chequeraSerie = chequeraPago.getChequeraCuota().getChequeraSerie();
        assert chequeraSerie != null;
        PersonaDto persona = chequeraSerie.getPersona();
        ComprobanteDto comprobante = comprobanteClient.findByComprobanteId(14);
        log.debug("Comprobante -> {}", comprobante.jsonify());

        if (comprobante.getFacturacionElectronica() == 0) {
            return false;
        }

        ChequeraFacturacionElectronicaDto chequeraFacturacionElectronica;
        try {
            chequeraFacturacionElectronica = chequeraFacturacionElectronicaClient.findByChequeraId(chequeraSerie.getChequeraId());
            log.debug("ChequeraFacturacionElectronica -> {}", chequeraFacturacionElectronica.jsonify());
        } catch (Exception e) {
            chequeraFacturacionElectronica = new ChequeraFacturacionElectronicaDto();
        }

        Integer tipoAfip = comprobante.getComprobanteAfipId();
        Integer puntoVenta = comprobante.getPuntoVenta();
        java.math.BigDecimal importePagado = chequeraPago.getImporte();

        Integer tipoDocumentoAfip = 80;
        String tipoDocumento = "CUIT";
        String apellido = chequeraFacturacionElectronica.getRazonSocial();
        String nombre = "";
        String numeroDocumento = chequeraFacturacionElectronica.getCuit().trim().replace("-", "");

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
        FacturacionDto facturacion = FacturacionDto.builder()
                .tipoDocumento(tipoDocumentoAfip)
                .documento(numeroDocumento)
                .tipoAfip(tipoAfip)
                .puntoVenta(puntoVenta)
                .total(importePagado.setScale(2, RoundingMode.HALF_UP))
                .neto(importePagado.setScale(2, RoundingMode.HALF_UP))
                .idCondicionIva(5)
                .build();

        log.info("Afip Test -> {}", facturacionAfipClient.test());
        log.debug("Facturacion -> {}", facturacion.jsonify());

        try {
            facturacion = facturacionAfipClient.facturador(facturacion);
            log.debug("Facturacion -> {}", facturacion.jsonify());
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
            FacturacionElectronicaDto facturacionElectronica = FacturacionElectronicaDto.builder()
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
            log.debug("FacturacionElectronica -> {}", facturacionElectronica.jsonify());
            facturacionElectronica = facturacionElectronicaClient.add(facturacionElectronica);
            log.debug("after add FacturacionElectronica");
            log.debug("FacturacionElectronica -> {}", facturacionElectronica.jsonify());
            return true;
        }
        return false;
    }

}
