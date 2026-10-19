package com.example.studyfocus.data.model

data class Flashcard (
    val id: String = "",
    val frente: String = "",
    val verso: String = "",
    val caixa: Int = 1,
    val proximaRevisao: Long = 0L,
    val disciplinaId: String = ""
)