package leafenterprise.leafcollector.br.domain

data class Adress(
    val adress: String,
    val num: String,
    val complement : String?,
    val district: String,
    val city: String,
    val state : String,
    val cep : String
)
