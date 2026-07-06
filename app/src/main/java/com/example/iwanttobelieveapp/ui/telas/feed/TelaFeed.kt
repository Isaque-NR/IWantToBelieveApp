package com.example.iwanttobelieveapp.ui.telas.feed


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.iwanttobelieveapp.ui.navegacao.Rotas

@Composable
fun TelaFeed(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Feed de Publicações")

        Button(
            onClick = {
                navController.navigate(Rotas.NOVA_PUBLI)
            }
        ) {
            Text("Nova Publicação")
        }

        Button(
            onClick = {
                navController.navigate(Rotas.PERFIL)
            }
        ) {
            Text("Meu Perfil")
        }

        Button(
            onClick = {
                navController.navigate(Rotas.LOGIN) {
                    popUpTo(Rotas.FEED) {
                        inclusive = true
                    }
                }
            }
        ) {
            Text("Sair")
        }
    }
}