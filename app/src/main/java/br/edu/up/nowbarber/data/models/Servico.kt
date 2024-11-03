package br.edu.up.nowbarber.data.models

data class Servico(
    val name: String,
    val price: String,  // preço formatado como String
    val imageResId: Int? = null // opcional, caso haja uma imagem associada ao serviço
)