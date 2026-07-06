package com.example.iwanttobelieveapp.ui.telas.nova_publi

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

@Composable
fun TelaNovaPubli(navController: NavController) {
    var descricao by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Nova Publicação")

        TextField(
            value = descricao,
            onValueChange = {
                descricao = it
            },
            label = {
                Text("Descrição da publicação")
            }
        )

        Button(
            onClick = {
                // Depois vamos abrir a galeria aqui
            }
        ) {
            Text("Selecionar imagem")
        }

        Button(
            onClick = {
                // Depois vamos enviar para Firebase Storage e salvar no Firestore
            }
        ) {
            Text("Publicar")
        }

        Button(
            onClick = {
                navController.popBackStack()
            }
        ) {
            Text("Voltar")
        }
    }
}