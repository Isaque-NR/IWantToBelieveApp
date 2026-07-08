package com.example.iwanttobelieveapp.ui.telas.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.iwanttobelieveapp.R
import com.example.iwanttobelieveapp.ui.navegacao.Rotas
import com.example.iwanttobelieveapp.ui.theme.Azul
import com.example.iwanttobelieveapp.ui.theme.Branco
import com.example.iwanttobelieveapp.ui.theme.VerdeEscuro
import com.example.iwanttobelieveapp.ui.theme.VerdePrincipal
import com.example.iwanttobelieveapp.viewmodel.AutenticacaoViewModel

@Composable
fun TelaLogin(
    navController: NavController,
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
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "I want to Believe",
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(24.dp))

        Image(
            painter = painterResource(id = R.drawable.icon),
            contentDescription = "Logo do app",
            modifier = Modifier.size(170.dp)
        )

        Spacer(modifier = Modifier.height(48.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.email),
                contentDescription = "Ícone de email",
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

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
                ),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.senha),
                contentDescription = "Ícone de senha",
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

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
                ),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        if (uiState.erro != null) {
            Text(
                text = uiState.erro ?: "",
                color = MaterialTheme.colorScheme.error
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        if (uiState.carregando) {
            CircularProgressIndicator()

            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            enabled = !uiState.carregando,
            onClick = {
                authViewModel.login(
                    email = email.trim(),
                    senha = senha
                )
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = VerdePrincipal,
                contentColor = Branco
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Entrar")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            enabled = !uiState.carregando,
            onClick = {
                authViewModel.limparEstado()
                navController.navigate(Rotas.REGISTRAR)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Azul,
                contentColor = Branco
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Criar conta")
        }
    }
}