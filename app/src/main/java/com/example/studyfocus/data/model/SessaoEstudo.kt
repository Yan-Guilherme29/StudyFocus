package com.example.studyfocus.data.model

data class SessaoEstudo (
    val id: String = "",
    val disciplinaId: String = "",
    val tarefaId: String? = null,
    val inicio: Long = 0L,
    val duracaoMin: Int = 0
)