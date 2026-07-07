package com.example.iwanttobelieveapp.ui.navegacao

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.iwanttobelieveapp.ui.telas.login.TelaLogin
import com.example.iwanttobelieveapp.ui.telas.feed.TelaFeed
import com.example.iwanttobelieveapp.ui.telas.perfil.TelaPerfil
import com.example.iwanttobelieveapp.ui.telas.registrar.TelaRegistrar
import com.example.iwanttobelieveapp.ui.telas.nova_publi.TelaNovaPubli
import com.example.iwanttobelieveapp.viewmodel.AutenticacaoViewModel
@Composable
fun NavegacaoApp() {
    val navController = rememberNavController()

    val authViewModel: AutenticacaoViewModel = viewModel()

    val startDestination = if (authViewModel.usuarioEstaLogado()) {
        Rotas.FEED
    } else {
        Rotas.LOGIN
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Rotas.LOGIN) {
            TelaLogin(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(Rotas.REGISTRAR) {
            TelaRegistrar(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(Rotas.FEED) {
            TelaFeed(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(Rotas.PERFIL) {
            TelaPerfil(navController)
        }

        composable(Rotas.NOVA_PUBLI) {
            TelaNovaPubli(navController)
        }
    }
}