package ar.edu.um.tesoreria.facturador.kotlin.model.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.math.BigDecimal
import java.time.OffsetDateTime

data class FacturacionElectronicaDto(

    var facturacionElectronicaId: Long? = null,
    var chequeraPagoId: Long? = null,
    var comprobanteId: Int? = null,
    var numeroComprobante: Long = 0,
    var personaId: BigDecimal? = null,
    var tipoDocumento: String? = null,
    var apellido: String? = null,
    var nombre: String? = null,
    var cuit: String? = null,
    var condicionIva: String = "",
    var importe: BigDecimal = BigDecimal.ZERO,
    var cae: String? = null,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    var fechaRecibo: OffsetDateTime? = null,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    var fechaVencimientoCae: OffsetDateTime? = null,
    var enviada: Byte = 0,
    var retries: Int = 0,
    var chequeraPago: ChequeraPagoDto? = null,
    var comprobante: ComprobanteDto? = null

) {

    class Builder {
        var facturacionElectronicaId: Long? = null
        var chequeraPagoId: Long? = null
        var comprobanteId: Int? = null
        var numeroComprobante: Long = 0
        var personaId: BigDecimal? = null
        var tipoDocumento: String? = null
        var apellido: String? = null
        var nombre: String? = null
        var cuit: String? = null
        var condicionIva: String = ""
        var importe: BigDecimal = BigDecimal.ZERO
        var cae: String? = null
        var fechaRecibo: OffsetDateTime? = null
        var fechaVencimientoCae: OffsetDateTime? = null
        var enviada: Byte = 0
        var retries: Int = 0
        var chequeraPago: ChequeraPagoDto? = null
        var comprobante: ComprobanteDto? = null

        fun facturacionElectronicaId(facturacionElectronicaId: Long?) =
            apply { this.facturacionElectronicaId = facturacionElectronicaId }

        fun chequeraPagoId(chequeraPagoId: Long?) = apply { this.chequeraPagoId = chequeraPagoId }
        fun comprobanteId(chequeraPagoId: Long?) = apply { this.comprobanteId = comprobanteId }
        fun numeroComprobante(numeroComprobante: Long) = apply { this.numeroComprobante = numeroComprobante }
        fun personaId(personaId: BigDecimal?) = apply { this.personaId = personaId }
        fun tipoDocumento(tipoDocumento: String?) = apply { this.tipoDocumento = tipoDocumento }
        fun apellido(apellido: String?) = apply { this.apellido = apellido }
        fun nombre(nombre: String?) = apply { this.nombre = nombre }
        fun cuit(cuit: String?) = apply { this.cuit = cuit }
        fun condicionIva(condicionIva: String) = apply { this.condicionIva = condicionIva }
        fun importe(importe: BigDecimal) = apply { this.importe = importe }
        fun cae(cae: String?) = apply { this.cae = cae }
        fun fechaRecibo(fechaRecibo: OffsetDateTime?) = apply { this.fechaRecibo = fechaRecibo }
        fun fechaVencimientoCae(fechaVencimientoCae: OffsetDateTime?) =
            apply { this.fechaVencimientoCae = fechaVencimientoCae }

        fun enviada(enviada: Byte) = apply { this.enviada = enviada }
        fun retries(retries: Int) = apply { this.retries = retries }
        fun chequeraPago(chequeraPago: ChequeraPagoDto?) = apply { this.chequeraPago = chequeraPago }
        fun comprobante(comprobante: ComprobanteDto?) = apply { this.comprobante = comprobante }

        fun build() = FacturacionElectronicaDto(
            facturacionElectronicaId,
            chequeraPagoId,
            comprobanteId,
            numeroComprobante,
            personaId,
            tipoDocumento,
            apellido,
            nombre,
            cuit,
            condicionIva,
            importe,
            cae,
            fechaRecibo,
            fechaVencimientoCae,
            enviada,
            retries,
            chequeraPago,
            comprobante
        )
    }

}

