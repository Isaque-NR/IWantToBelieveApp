package com.example.iwanttobelieveapp.ui.telas.feed


import android.view.Surface
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.iwanttobelieveapp.R
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.iwanttobelieveapp.data.model.PostagemApp
import com.example.iwanttobelieveapp.ui.navegacao.Rotas
import com.example.iwanttobelieveapp.viewmodel.AutenticacaoViewModel
import com.example.iwanttobelieveapp.viewmodel.FeedViewModel
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.request.ImageRequest
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import com.example.iwanttobelieveapp.ui.theme.Azul
import com.example.iwanttobelieveapp.ui.theme.VerdePrincipal
import com.example.iwanttobelieveapp.ui.theme.VerdeEscuro
import com.example.iwanttobelieveapp.ui.theme.VermelhoSair
import com.example.iwanttobelieveapp.ui.theme.Branco

@Composable
fun TelaFeed(
    navController: NavController,
    authViewModel: AutenticacaoViewModel,
    feedViewModel: FeedViewModel = viewModel()
) {
    val uiState by feedViewModel.uiState.collectAsState()

    Scaffold(
        bottomBar = {
            Surface(
                tonalElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            navController.navigate(Rotas.NOVA_PUBLI)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = VerdePrincipal,
                            contentColor = Branco
                        )
                    ) {
                        Text("Nova Publicação")
                    }

                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            navController.navigate(Rotas.PERFIL)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Azul,
                            contentColor = Branco
                        )
                    ) {
                        Text("Perfil")
                    }

                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            authViewModel.sair()

                            navController.navigate(Rotas.LOGIN) {
                                popUpTo(Rotas.FEED) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = VermelhoSair,
                            contentColor = Branco
                        )
                    ) {
                        Text("Sair")
                    }
                }
            }
        }
    ) { paddingInterno ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingInterno)
                .statusBarsPadding()
                .padding(horizontal = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 16.dp),
                contentAlignment = Alignment.Center,

            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon),
                        contentDescription = "Símbolo do app",
                        modifier = Modifier.size(36.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "I want to believe",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            if (uiState.carregando) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            if (uiState.erro != null) {
                Text(
                    text = uiState.erro ?: ""
                )
            }

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(bottom = 12.dp)
            ) {
                items(uiState.postagens) { postagem ->
                    PostagemPubli(postagem = postagem)
                }
            }
        }
    }
}

@Composable
fun PostagemPubli(postagem: PostagemApp) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = VerdeEscuro.copy(alpha = 0.10f)
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = "Usuário: " + postagem.autorNome,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(postagem.imagemUrl.trim())
                    .crossfade(true)
                    .build(),
                contentDescription = "Imagem da postagem",
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = postagem.descricao
            )
        }
    }
}