package ar.edu.um.tesoreria.facturador.kotlin.model.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import java.time.OffsetDateTime

data class ChequeraPagoDto(

    @JsonProperty(value = "chequera_pago_id")
    var chequeraPagoId: Long? = null,

    @JsonProperty(value = "chequera_cuota_id")
    var chequeraCuotaId: Long? = null,

    @JsonProperty(value = "facultad_id")
    var facultadId: Int? = null,

    @JsonProperty(value = "tipo_chequera_id")
    var tipoChequeraId: Int? = null,

    @JsonProperty(value = "chequera_serie_id")
    var chequeraSerieId: Long? = null,

    @JsonProperty(value = "producto_id")
    var productoId: Int? = null,

    @JsonProperty(value = "alternativa_id")
    var alternativaId: Int? = null,

    @JsonProperty(value = "cuota_id")
    var cuotaId: Int? = null,

    var orden: Int? = null,
    var mes: Int = 0,
    var anho: Int = 0,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    var fecha: OffsetDateTime? = null,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    var acreditacion: OffsetDateTime? = null,

    var importe: BigDecimal = BigDecimal.ZERO,
    var path: String = "",
    var archivo: String = "",
    var observaciones: String = "",

    @JsonProperty(value = "archivo_banco_id")
    var archivoBancoId: Long? = null,

    @JsonProperty(value = "archivo_banco_id_acreditacion")
    var archivoBancoIdAcreditacion: Long? = null,

    var verificador: Int = 0,

    @JsonProperty(value = "tipo_pago_id")
    var tipoPagoId: Int? = null

)
