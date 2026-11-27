package com.example.studyfocus.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyfocus.data.repository.AuthRepository
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AuthUiState(
    val carregando: Boolean = false,
    val erro: String? = null,
    val autenticado: Boolean = false
)

class AuthViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _estado = MutableStateFlow(AuthUiState())
    val estado: StateFlow<AuthUiState> = _estado.asStateFlow()

    fun entrar(email: String, senha: String) {
        viewModelScope.launch {
            _estado.value = AuthUiState(carregando = true)
            try {
                repository.entrar(email, senha)
                _estado.value = AuthUiState(autenticado = true)
            } catch (e: Exception) {
                _estado.value = AuthUiState(erro = mensagemDeErro(e))
            }
        }
    }

    fun cadastrar(nome: String, email: String, senha: String) {
        viewModelScope.launch {
            _estado.value = AuthUiState(carregando = true)

            try {
                repository.cadastrar(nome, email, senha)
                _estado.value = AuthUiState(autenticado = true)
            } catch (e: Exception) {
                _estado.value = AuthUiState(erro = mensagemDeErro(e))
            }
        }
    }

    private fun mensagemDeErro(e: Exception): String = when (e) {
        is FirebaseAuthInvalidUserException,
        is FirebaseAuthInvalidCredentialsException -> "Email ou senha incorretos."
        is FirebaseAuthUserCollisionException -> "Já existe uma conta com esse email."
        is FirebaseAuthWeakPasswordException -> "A senha precisa ter pelo menos 6 caracteres."
        is FirebaseNetworkException -> "Sem conexão com a internet."
        else -> "Não foi possível concluir. Tente novamente."
    }
}