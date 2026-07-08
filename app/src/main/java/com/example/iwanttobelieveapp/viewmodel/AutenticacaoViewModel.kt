package com.example.iwanttobelieveapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iwanttobelieveapp.data.repositorio.RepositorioAutenticacao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException
data class AuthUiState(
    val carregando: Boolean = false,
    val erro: String? = null,
    val cadastroConcluido: Boolean = false,
    val loginConcluido: Boolean = false
)

class AutenticacaoViewModel : ViewModel() {

    private val repository = RepositorioAutenticacao()

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun cadastrar(
        nome: String,
        email: String,
        senha: String
    ) {
        if (nome.isBlank() || email.isBlank() || senha.isBlank()) {
            _uiState.value = AuthUiState(
                erro = "Preencha todos os campos."
            )
            return
        }

        if (senha.length < 6) {
            _uiState.value = AuthUiState(
                erro = "A senha precisa ter pelo menos 6 caracteres."
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = AuthUiState(
                carregando = true
            )

            val resultado = repository.cadastrarUsuario(
                nome = nome,
                email = email,
                senha = senha
            )

            _uiState.value = if (resultado.isSuccess) {
                AuthUiState(
                    loginConcluido = true
                )
            } else {
                AuthUiState(
                    erro = traduzirErroFirebase(resultado.exceptionOrNull())
                )
            }
        }
    }

    fun login(
        email: String,
        senha: String
    ) {
        if (email.isBlank() || senha.isBlank()) {
            _uiState.value = AuthUiState(
                erro = "Preencha e-mail e senha."
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = AuthUiState(
                carregando = true
            )

            val resultado = repository.loginUsuario(
                email = email,
                senha = senha
            )

            _uiState.value = if (resultado.isSuccess) {
                AuthUiState(
                    loginConcluido = true
                )
            } else {
                AuthUiState(
                    erro = traduzirErroFirebase(resultado.exceptionOrNull())
                )
            }
        }
    }

    fun usuarioEstaLogado(): Boolean {
        return repository.usuarioLogado() != null
    }

    fun sair() {
        repository.sair()
        _uiState.value = AuthUiState()
    }

    fun limparEstado() {
        _uiState.value = AuthUiState()
    }

    private fun traduzirErroFirebase(erro: Throwable?): String {
        if (erro is FirebaseNetworkException) {
            return "Sem conexão com a internet. Verifique sua rede e tente novamente."
        }

        if (erro is FirebaseAuthException) {
            return when (erro.errorCode) {
                "ERROR_INVALID_EMAIL" ->
                    "O e-mail informado não é válido."

                "ERROR_INVALID_CREDENTIAL",
                "ERROR_WRONG_PASSWORD",
                "ERROR_USER_NOT_FOUND" ->
                    "E-mail ou senha incorretos."

                "ERROR_EMAIL_ALREADY_IN_USE" ->
                    "Este e-mail já está cadastrado."

                "ERROR_WEAK_PASSWORD" ->
                    "A senha precisa ter pelo menos 6 caracteres."

                "ERROR_USER_DISABLED" ->
                    "Esta conta foi desativada."

                "ERROR_TOO_MANY_REQUESTS" ->
                    "Muitas tentativas. Aguarde um pouco e tente novamente."

                else ->
                    "Não foi possível concluir a operação. Tente novamente."
            }
        }

        return "Ocorreu um erro inesperado. Tente novamente."
    }
}