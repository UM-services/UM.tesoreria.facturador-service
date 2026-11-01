package um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraFacturacionElectronica.infrastructure.client.mapper;

import org.springframework.stereotype.Component;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraFacturacionElectronica.domain.model.ChequeraFacturacionElectronica;
import um.tesoreria.facturador.hexagonal.facturacion.datos.chequeraFacturacionElectronica.infrastructure.client.dto.ChequeraFacturacionElectronicaResponse;

@Component
public class ChequeraFacturacionElectronicaMapper {

    public ChequeraFacturacionElectronica toDomain(ChequeraFacturacionElectronicaResponse response) {
        if (response == null) return null;

        var builder = ChequeraFacturacionElectronica.builder()
                .chequeraFacturacionElectronicaId(response.getChequeraFacturacionElectronicaId())
                .chequeraId(response.getChequeraId())
                .domicilio(response.getDomicilio())
                .email(response.getEmail());

        if (response.getCuit() != null) builder.cuit(response.getCuit());
        if (response.getRazonSocial() != null) builder.razonSocial(response.getRazonSocial());
        if (response.getCondicionIva() != null) builder.condicionIva(response.getCondicionIva());

        return builder.build();
    }

}
