package um.tesoreria.facturador.kotlin.model.dto

data class ArancelTipoDto(

    var arancelTipoId: Int? = null,
    var descripcion: String = "",
    var medioArancel: Byte = 0,
    var arancelTipoIdCompleto: Int? = null

)
