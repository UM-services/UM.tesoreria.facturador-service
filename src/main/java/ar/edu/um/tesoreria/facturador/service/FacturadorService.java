package ar.edu.um.tesoreria.facturador.service;

import ar.edu.um.tesoreria.facturador.client.ChequeraFacturacionElectronicaClient;
import ar.edu.um.tesoreria.facturador.client.ComprobanteClient;
import ar.edu.um.tesoreria.facturador.client.FacturacionElectronicaClient;
import ar.edu.um.tesoreria.facturador.kotlin.model.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
@Slf4j
public class FacturadorService {

    private final ComprobanteClient comprobanteClient;
    private final ChequeraFacturacionElectronicaClient chequeraFacturacionElectronicaClient;
    private final FacturacionElectronicaClient facturacionElectronicaClient;

    public FacturadorService(ComprobanteClient comprobanteClient, ChequeraFacturacionElectronicaClient chequeraFacturacionElectronicaClient, FacturacionElectronicaClient facturacionElectronicaClient) {
        this.comprobanteClient = comprobanteClient;
        this.chequeraFacturacionElectronicaClient = chequeraFacturacionElectronicaClient;
        this.facturacionElectronicaClient = facturacionElectronicaClient;
    }

    public boolean facturaCuota(ChequeraPagoDto chequeraPago) {

        var empresaCuit = "30-51859446-6";
        var empresaRazonSocial = "UNIVERSIDAD DE MENDOZA";

        var chequeraSerie = chequeraPago.getChequeraCuota().getChequeraSerie();
        var persona = chequeraSerie.getPersona();
        var comprobante = comprobanteClient.findByComprobanteId(14);

        try {
            log.debug("comprobante={}", JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(comprobante));
        } catch (JsonProcessingException e) {
            log.debug("comprobante=null");
        }

        if (comprobante.getFacturacionElectronica() == 0) {
            return false;
        }

        var chequeraFacturacionElectronica = chequeraFacturacionElectronicaClient.findByChequeraId(chequeraSerie.getChequeraId());

        var tipoAfip = comprobante.getComprobanteAfipId();
        var puntoVenta = comprobante.getPuntoVenta();
        var importePagado = chequeraPago.getImporte();

        var tipoDocumentoAfip = 80;
        var tipoDocumento = "CUIT";
        var apellido = chequeraFacturacionElectronica.getRazonSocial();
        var nombre = "";
        var numeroDocumento = chequeraFacturacionElectronica.getCuit().trim().replace("-", "");

        FacturacionDto facturacionDTO = new FacturacionDto.Builder()
                .tipoDocumento(tipoDocumentoAfip)
                .documento(numeroDocumento)
                .tipoAfip(tipoAfip)
                .puntoVenta(puntoVenta)
                .total(importePagado.setScale(2, RoundingMode.HALF_UP))
                .exento(importePagado.setScale(2, RoundingMode.HALF_UP))
                .build();

        try {
            log.debug("facturacionDTO={}", JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(facturacionDTO));
        } catch (JsonProcessingException e) {
            log.debug("facturacionDTO=null");
        }

        try {
            facturacionDTO = facturacionElectronicaService.facturar(facturacionDTO, empresa.getNegocioId());
            try {
                log.debug("facturacionDTO (after)={}", JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(facturacionDTO));
            } catch (JsonProcessingException e) {
                log.debug("facturacionDTO=null");
            }
        } catch (WebClientResponseException e) {
            log.debug("Servicio de Facturación NO disponible");
            return false;
        }

        if (facturacionDTO.getResultado().equals("A")) {
            // Convierte fechas
            SimpleDateFormat formatoInDate = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat formatoOutDate = new SimpleDateFormat("ddMMyyyy");
            Date vencimientoCae = null;
            try {
                vencimientoCae = formatoInDate.parse(facturacionDTO.getVencimientoCae());
            } catch (ParseException e) {
            }
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("ddMMyyyy");
            // Registra el resultado de la AFIP
            FacturacionElectronicaDto facturacionElectronica = new FacturacionElectronicaDto.Builder()
                    .comprobanteId(comprobanteId)
                    .puntoVenta(facturacionDTO.getPuntoVenta())
                    .numeroComprobante(facturacionDTO.getNumeroComprobante())
                    .clienteId(cliente.getClienteId())
                    .cuit("")
                    .total(facturacionDTO.getTotal())
                    .exento(facturacionDTO.getExento())
                    .neto(facturacionDTO.getNeto())
                    .neto105(facturacionDTO.getNeto105())
                    .iva(facturacionDTO.getIva())
                    .iva105(facturacionDTO.getIva105())
                    .cae(facturacionDTO.getCae())
                    .fecha(ToolService.dateAbsoluteArgentina().format(dateTimeFormatter))
                    .caeVencimiento(formatoOutDate.format(vencimientoCae))
                    .tipoDocumento(facturacionDTO.getTipoDocumento())
                    .numeroDocumento(new BigDecimal(facturacionDTO.getDocumento()))
                    .build();
            facturacionElectronica = facturacionElectronicaClient.add(facturacionElectronica);
            try {
                log.debug("registroCae={}", JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(registroCae));
            } catch (JsonProcessingException e) {
                log.debug("registroCae=null");
            }
            return true;
        }

        return false;

    }


}
