package com.example.iwanttobelieveapp.ui.telas.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.iwanttobelieveapp.ui.navegacao.Rotas
import com.example.iwanttobelieveapp.viewmodel.AutenticacaoViewModel

@Composable
fun TelaLogin(navController: NavController,
              authViewModel: AutenticacaoViewModel
) {
    var email by remember {
        mutableStateOf("")
    }

    var senha by remember {
        mutableStateOf("")
    }
    val uiState by authViewModel.uiState.collectAsState()

    LaunchedEffect(uiState.loginConcluido) {
        if (uiState.loginConcluido) {
            authViewModel.limparEstado()

            navController.navigate(Rotas.FEED) {
                popUpTo(Rotas.LOGIN) {
                    inclusive = true
                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("I want to believe")
        // adicionar imagem icone
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text("E-mail")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            )
        )

        OutlinedTextField(
            value = senha,
            onValueChange = {
                senha = it
            },
            label = {
                Text("Senha")
            },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            )
        )
        if (uiState.erro != null) {
            Text(
                text = uiState.erro ?: ""
            )
        }

        if (uiState.carregando) {
            CircularProgressIndicator()
        }
        Button(
            enabled = !uiState.carregando,
            onClick = {
                authViewModel.login(
                    email = email.trim(),
                    senha = senha
                )
            }
        ) {
            Text("Entrar")
        }

        Button(
            enabled = !uiState.carregando,
            onClick = {
                authViewModel.limparEstado()
                navController.navigate(Rotas.REGISTRAR)
            }
        ) {
            Text("Criar conta")
        }
    }
}