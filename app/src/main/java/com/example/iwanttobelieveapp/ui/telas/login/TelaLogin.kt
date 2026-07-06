package com.example.iwanttobelieveapp.ui.telas.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.iwanttobelieveapp.ui.navegacao.Rotas

@Composable
fun TelaLogin(navController: NavController) {
    var email by remember {
        mutableStateOf("")
    }

    var senha by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("I want to believe")

        TextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text("E-mail")
            }
        )

        TextField(
            value = senha,
            onValueChange = {
                senha = it
            },
            label = {
                Text("Senha")
            }
        )

        Button(
            onClick = {
                navController.navigate(Rotas.FEED)
            }
        ) {
            Text("Entrar")
        }

        Button(
            onClick = {
                navController.navigate(Rotas.REGISTRAR)
            }
        ) {
            Text("Criar conta")
        }
    }
}