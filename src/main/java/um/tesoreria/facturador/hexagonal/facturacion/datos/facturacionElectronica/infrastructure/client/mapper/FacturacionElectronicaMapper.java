package um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.infrastructure.client.mapper;

import org.springframework.stereotype.Component;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.domain.model.FacturacionElectronica;
import um.tesoreria.facturador.hexagonal.facturacion.datos.facturacionElectronica.infrastructure.client.dto.FacturacionElectronicaDto;

@Component
public class FacturacionElectronicaMapper {

    public FacturacionElectronicaDto toDto(FacturacionElectronica domain) {
        if (domain == null) return null;

        var builder = FacturacionElectronicaDto.builder()
                .facturacionElectronicaId(domain.getFacturacionElectronicaId())
                .chequeraPagoId(domain.getChequeraPagoId())
                .comprobanteId(domain.getComprobanteId())
                .personaId(domain.getPersonaId())
                .tipoDocumento(domain.getTipoDocumento())
                .apellido(domain.getApellido())
                .nombre(domain.getNombre())
                .cuit(domain.getCuit())
                .cae(domain.getCae())
                .fechaRecibo(domain.getFechaRecibo())
                .fechaVencimientoCae(domain.getFechaVencimientoCae());

        if (domain.getNumeroComprobante() != null) builder.numeroComprobante(domain.getNumeroComprobante());
        if (domain.getCondicionIva() != null) builder.condicionIva(domain.getCondicionIva());
        if (domain.getImporte() != null) builder.importe(domain.getImporte());
        if (domain.getEnviada() != null) builder.enviada(domain.getEnviada());
        if (domain.getRetries() != null) builder.retries(domain.getRetries());

        return builder.build();
    }

    public FacturacionElectronica toDomain(FacturacionElectronicaDto dto) {
        if (dto == null) return null;

        var builder = FacturacionElectronica.builder()
                .facturacionElectronicaId(dto.getFacturacionElectronicaId())
                .chequeraPagoId(dto.getChequeraPagoId())
                .comprobanteId(dto.getComprobanteId())
                .personaId(dto.getPersonaId())
                .tipoDocumento(dto.getTipoDocumento())
                .apellido(dto.getApellido())
                .nombre(dto.getNombre())
                .cuit(dto.getCuit())
                .cae(dto.getCae())
                .fechaRecibo(dto.getFechaRecibo())
                .fechaVencimientoCae(dto.getFechaVencimientoCae());

        if (dto.getNumeroComprobante() != null) builder.numeroComprobante(dto.getNumeroComprobante());
        if (dto.getCondicionIva() != null) builder.condicionIva(dto.getCondicionIva());
        if (dto.getImporte() != null) builder.importe(dto.getImporte());
        if (dto.getEnviada() != null) builder.enviada(dto.getEnviada());
        if (dto.getRetries() != null) builder.retries(dto.getRetries());

        return builder.build();
    }

}
