package com.example.studyfocus.data.repository

import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {

    val usuarioLogado: Boolean
        get() = auth.currentUser != null

    suspend fun entrar(email: String, senha: String) {
        auth.signInWithEmailAndPassword(email, senha).await()
    }

    suspend fun cadastrar(nome: String, email: String, senha: String) {
        val resultado = auth.createUserWithEmailAndPassword(email, senha).await()

        resultado.user?.updateProfile(
            userProfileChangeRequest {
                displayName = nome
            }
        )?.await()
    }


    fun sair() {
        auth.signOut()
    }
}
