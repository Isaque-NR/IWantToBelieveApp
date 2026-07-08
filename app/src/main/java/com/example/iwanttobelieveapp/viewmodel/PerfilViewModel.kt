package com.example.iwanttobelieveapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iwanttobelieveapp.data.model.UsuarioApp
import com.example.iwanttobelieveapp.data.repositorio.RepositorioUsuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PerfilUiState(
    val carregando: Boolean = false,
    val usuario: UsuarioApp? = null,
    val erro: String? = null
)

class PerfilViewModel : ViewModel() {

    private val repository = RepositorioUsuario()

    private val _uiState = MutableStateFlow(PerfilUiState())
    val uiState: StateFlow<PerfilUiState> = _uiState.asStateFlow()

    fun carregarPerfil() {
        viewModelScope.launch {
            _uiState.value = PerfilUiState(
                carregando = true
            )

            val resultado = repository.buscarUsuarioLogado()

            _uiState.value = if (resultado.isSuccess) {
                PerfilUiState(
                    usuario = resultado.getOrNull()
                )
            } else {
                PerfilUiState(
                    erro = resultado.exceptionOrNull()?.message
                        ?: "Erro ao carregar perfil."
                )
            }
        }
    }
}