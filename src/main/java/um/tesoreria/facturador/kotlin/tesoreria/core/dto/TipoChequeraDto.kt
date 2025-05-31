package um.tesoreria.facturador.kotlin.tesoreria.core.dto

data class TipoChequeraDto(

    var tipoChequeraId: Int? = null,
    var nombre: String = "",
    var prefijo: String = "",
    var geograficaId: Int = 1,
    var claseChequeraId: Int = 2,
    var imprimir: Byte = 0,
    var contado: Byte = 0,
    var multiple: Byte = 0,
    var emailCopia: String? = null,
    var geografica: GeograficaDto? = null,
    var claseChequera: ClaseChequeraDto? = null

)
