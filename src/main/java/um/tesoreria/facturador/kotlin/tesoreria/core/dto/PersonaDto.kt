package um.tesoreria.facturador.kotlin.tesoreria.core.dto

import java.math.BigDecimal

data class PersonaDto(

    var uniqueId: Long? = null,
    var personaId: BigDecimal? = null,
    var documentoId: Int? = null,
    var apellido: String? = null,
    var nombre: String? = null,
    var sexo: String? = null,
    var primero: Byte = 0,
    var cuit: String = "",
    var cbu: String = "",
    var password: String? = null

)
