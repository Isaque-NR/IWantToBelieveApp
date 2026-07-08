package com.example.iwanttobelieveapp.ui.telas.nova_publi

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.iwanttobelieveapp.R
import com.example.iwanttobelieveapp.ui.theme.Azul
import com.example.iwanttobelieveapp.ui.theme.Branco
import com.example.iwanttobelieveapp.ui.theme.VerdeEscuro
import com.example.iwanttobelieveapp.ui.theme.VerdePrincipal
import com.example.iwanttobelieveapp.viewmodel.FeedViewModel

@Composable
fun TelaNovaPubli(
    navController: NavController,
    feedViewModel: FeedViewModel
) {
    var descricao by remember {
        mutableStateOf("")
    }

    var imagemUrl by remember {
        mutableStateOf("")
    }

    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Azul,
                        contentColor = Branco
                    )
                ) {
                    Text("Voltar")
                }
            }
        }
    ) { paddingInterno ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingInterno)
                .statusBarsPadding()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "I want to believe",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(20.dp))

            Image(
                painter = painterResource(id = R.drawable.icon),
                contentDescription = "Logo do app",
                modifier = Modifier.size(150.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Nova Publicação",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = descricao,
                onValueChange = {
                    descricao = it
                },
                label = {
                    Text("Descrição da publicação")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = imagemUrl,
                onValueChange = {
                    imagemUrl = it
                },
                label = {
                    Text("URL da imagem")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                enabled = descricao.isNotBlank() && imagemUrl.isNotBlank(),
                onClick = {
                    feedViewModel.adicionarPostagemFake(
                        descricao = descricao.trim(),
                        imagemUrl = imagemUrl.trim()
                    )

                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = VerdePrincipal,
                    contentColor = Branco
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Publicar")
            }
        }
    }
}