package um.tesoreria.facturador.kotlin.tesoreria.core.dto

data class ChequeraFacturacionElectronicaDto(

    var chequeraFacturacionElectronicaId: Long? = null,
    var chequeraId: Long? = null,
    var cuit: String = "",
    var razonSocial: String = "",
    var domicilio: String = "",
    var email: String = "",
    var condicionIva: String = ""

)
