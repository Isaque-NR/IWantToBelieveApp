package com.example.iwanttobelieveapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iwanttobelieveapp.R
import com.example.iwanttobelieveapp.data.model.PostagemApp
import com.example.iwanttobelieveapp.data.repositorio.RepositorioUsuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


data class FeedUiState(
    val carregando: Boolean = false,
    val postagens: List<PostagemApp> = emptyList(),
    val erro: String? = null
)

class FeedViewModel : ViewModel() {

    private val repositorioUsuario = RepositorioUsuario()
    private val _uiState = MutableStateFlow(
        FeedUiState(
            postagens = listOf(
                PostagemApp(
                    id = "1",
                    autorNome = "Luke Ceu Andante",
                    descricao = "Como assim esse jogo ainda ta vivo?",
                    imagemUrl = "https://bnetcmsus-a.akamaihd.net/cms/page_media/P0LDSEKBT3PX1744352023860.png",
                    criadoEm = System.currentTimeMillis()
                ),
                PostagemApp(
                    id = "2",
                    autorNome = "R2-D2",
                    descricao = "Na tranquilidade jogando stardew e pescando de boas",
                    imagemUrl = "https://www.bichosgeeks.com/wp-content/uploads/2018/07/Bichos-geeks-como-pescar-no-stardew-valley2.jpg",
                    criadoEm = System.currentTimeMillis() - 60000
                ),
                PostagemApp(
                    id = "3",
                    autorNome = "Kenobi",
                    descricao = "Acho que estou morto...",
                    imagemUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRVW63nIwpMZY6GmMvTbS89Az69ywYJtixRIIi3CMiTI2TruOSznKgilas&s=10",
                    criadoEm = System.currentTimeMillis() - 120000
                )
            )
        )
    )

    val uiState: StateFlow<FeedUiState> = _uiState.asStateFlow()

    fun adicionarPostagemFake(
        descricao: String,
        imagemUrl: String
    ) {
        viewModelScope.launch {
            val usuarioLogado = repositorioUsuario.buscarUsuarioLogado().getOrNull()

            val novaPostagem = PostagemApp(
                id = System.currentTimeMillis().toString(),
                autorNome = usuarioLogado?.nome ?: "Usuário Logado",
                descricao = descricao,
                imagemUrl = imagemUrl,
                autorUid = usuarioLogado?.uid ?: "",
                criadoEm = System.currentTimeMillis()
            )

            _uiState.value = _uiState.value.copy(
                postagens = listOf(novaPostagem) + _uiState.value.postagens
            )
        }
    }
}
