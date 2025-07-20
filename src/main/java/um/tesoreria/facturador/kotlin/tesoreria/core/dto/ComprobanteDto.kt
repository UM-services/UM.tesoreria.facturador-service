package um.tesoreria.facturador.kotlin.tesoreria.core.dto

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.json.JsonMapper

data class ComprobanteDto(

    var comprobanteId: Int? = null,
    var descripcion: String = "",
    var tipoTransaccionId: Int? = null,
    var ordenPago: Byte = 0,
    var aplicaPendiente: Byte = 0,
    var cuentaCorriente: Byte = 0,
    var debita: Byte = 0,
    var diasVigencia: Long = 0,
    var facturacionElectronica: Byte = 0,
    var comprobanteAfipId: Int? = null,
    var puntoVenta: Int? = null,
    var letraComprobante: String? = null,
    var comprobanteAfip: ComprobanteAfipDto? = null,

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
