package um.tesoreria.facturador.kotlin.tesoreria.core.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.json.JsonMapper
import java.math.BigDecimal
import java.time.OffsetDateTime

data class ChequeraPagoDto(

    var chequeraPagoId: Long? = null,
    var chequeraCuotaId: Long? = null,
    var facultadId: Int? = null,
    var tipoChequeraId: Int? = null,
    var chequeraSerieId: Long? = null,
    var productoId: Int? = null,
    var alternativaId: Int? = null,
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
    var archivoBancoId: Long? = null,
    var archivoBancoIdAcreditacion: Long? = null,
    var verificador: Int = 0,
    var tipoPagoId: Int? = null,

    var tipoPago: TipoPagoDto? = null,
    var producto: ProductoDto? = null,
    var chequeraCuota: ChequeraCuotaDto? = null

) {

    fun jsonify(): String {
        try {
            return JsonMapper
                .builder()
                .findAndAddModules()
                .build()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(this)
        } catch (e: JsonProcessingException) {
            return "jsonify error ${e.message}"
        }
    }

}
