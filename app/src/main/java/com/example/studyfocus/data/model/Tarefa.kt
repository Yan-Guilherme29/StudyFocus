package com.example.studyfocus.data.model

data class Tarefa (
    val id: String = "",
    val titulo: String = "",
    val descricao: String = "",
    val prazo: Long = 0L,
    val prioridade: Prioridade = Prioridade.BAIXA,
    val status: StatusTarefa = StatusTarefa.PENDENTE,
    val disciplinaId: String = ""
)
