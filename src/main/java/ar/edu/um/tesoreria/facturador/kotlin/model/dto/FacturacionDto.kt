package ar.edu.um.tesoreria.facturador.kotlin.model.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class FacturacionDto(

    @JsonProperty(value = "tipo_documento")
    var tipoDocumento: Int = 0,

    var documento: String = "0",

    @JsonProperty(value = "tipo_afip")
    var tipoAfip: Int = 0,

    @JsonProperty(value = "punto_venta")
    var puntoVenta: Int = 0,
    var total: BigDecimal = BigDecimal.ZERO,
    var exento: BigDecimal = BigDecimal.ZERO,
    var neto: BigDecimal = BigDecimal.ZERO,
    var neto105: BigDecimal = BigDecimal.ZERO,
    var iva: BigDecimal = BigDecimal.ZERO,
    var iva105: BigDecimal = BigDecimal.ZERO,
    var resultado: String = "",
    var cae: String = "",

    @JsonProperty(value = "vencimiento_cae")
    var vencimientoCae: String = "",

    @JsonProperty(value = "numero_comprobante")
    var numeroComprobante: Long = 0,

    // Comprobante Asociado
    @JsonProperty(value = "asociado_tipo_afip")
    var asociadoTipoAfip: Int? = null,

    @JsonProperty(value = "asociado_punto_venta")
    var asociadoPuntoVenta: Int? = null,

    @JsonProperty(value = "asociado_numero_comprobante")
    var asociadoNumeroComprobante: Long? = null,

    @JsonProperty(value = "asociado_fecha_comprobante")
    var asociadoFechaComprobante: String? = null

) {
    data class Builder(
        var tipoDocumento: Int = 0,
        var documento: String = "0",
        var tipoAfip: Int = 0,
        var puntoVenta: Int = 0,
        var total: BigDecimal = BigDecimal.ZERO,
        var exento: BigDecimal = BigDecimal.ZERO,
        var neto: BigDecimal = BigDecimal.ZERO,
        var neto105: BigDecimal = BigDecimal.ZERO,
        var iva: BigDecimal = BigDecimal.ZERO,
        var iva105: BigDecimal = BigDecimal.ZERO,
        var resultado: String = "",
        var cae: String = "",
        var vencimientoCae: String = "",
        var numeroComprobante: Long = 0,
        var asociadoTipoAfip: Int? = null,
        var asociadoPuntoVenta: Int? = null,
        var asociadoNumeroComprobante: Long? = null,
        var asociadoFechaComprobante: String? = null,
    ) {
        fun tipoDocumento(tipoDocumento: Int) = apply { this.tipoDocumento = tipoDocumento }
        fun documento(documento: String) = apply { this.documento = documento }
        fun tipoAfip(tipoAfip: Int) = apply { this.tipoAfip = tipoAfip }
        fun puntoVenta(puntoVenta: Int) = apply { this.puntoVenta = puntoVenta }
        fun total(total: BigDecimal) = apply { this.total = total }
        fun exento(exento: BigDecimal) = apply { this.exento = exento }
        fun neto(neto: BigDecimal) = apply { this.neto = neto }
        fun neto105(neto105: BigDecimal) = apply { this.neto105 = neto105 }
        fun iva(iva: BigDecimal) = apply { this.iva = iva }
        fun iva105(iva105: BigDecimal) = apply { this.iva105 = iva105 }
        fun resultado(resultado: String) = apply { this.resultado = resultado }
        fun cae(cae: String) = apply { this.cae = cae }
        fun vencimientoCae(vencimientoCae: String) = apply { this.vencimientoCae = vencimientoCae }
        fun numeroComprobate(numeroComprobante: Long) = apply { this.numeroComprobante = numeroComprobante }
        fun asociadoTipoAfip(asociadoTipoAfip: Int?) = apply { this.asociadoTipoAfip = asociadoTipoAfip }
        fun asociadoPuntoVenta(asociadoPuntoVenta: Int?) = apply { this.asociadoPuntoVenta = asociadoPuntoVenta }
        fun asociadoNumeroComprobante(asociadoNumeroComprobante: Long?) =
            apply { this.asociadoNumeroComprobante = asociadoNumeroComprobante }

        fun asociadoFechaComprobante(asociadoFechaComprobante: String?) =
            apply { this.asociadoFechaComprobante = asociadoFechaComprobante }

        fun build() = FacturacionDto(
            tipoDocumento,
            documento,
            tipoAfip,
            puntoVenta,
            total,
            exento,
            neto,
            neto105,
            iva,
            iva105,
            resultado,
            cae,
            vencimientoCae,
            numeroComprobante,
            asociadoTipoAfip,
            asociadoPuntoVenta,
            asociadoNumeroComprobante,
            asociadoFechaComprobante
        )
    }
}

