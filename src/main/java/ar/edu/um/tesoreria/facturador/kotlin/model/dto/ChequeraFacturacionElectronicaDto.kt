package ar.edu.um.tesoreria.facturador.kotlin.model.dto

data class ChequeraFacturacionElectronicaDto(

    var chequeraFacturacionElectronicaId: Long? = null,
    var chequeraId: Long? = null,
    var cuit: String = "",
    var razonSocial: String = "",
    var domicilio: String = "",
    var email: String = "",
    var condicionIva: String = ""

)
