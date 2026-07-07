package com.example.iwanttobelieveapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iwanttobelieveapp.data.repositorio.RepositorioAutenticacao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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
                    cadastroConcluido = true
                )
            } else {
                AuthUiState(
                    erro = resultado.exceptionOrNull()?.message
                        ?: "Erro ao cadastrar usuário."
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
                    erro = resultado.exceptionOrNull()?.message
                        ?: "Erro ao fazer login."
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
}