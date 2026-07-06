package com.example.iwanttobelieveapp.ui.navegacao

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.iwanttobelieveapp.ui.telas.login.TelaLogin
import com.example.iwanttobelieveapp.ui.telas.feed.TelaFeed
import com.example.iwanttobelieveapp.ui.telas.perfil.TelaPerfil
import com.example.iwanttobelieveapp.ui.telas.registrar.TelaRegistrar
import com.example.iwanttobelieveapp.ui.telas.nova_publi.TelaNovaPubli

@Composable
fun NavegacaoApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Rotas.LOGIN
    ) {
        composable(Rotas.LOGIN) {
            TelaLogin(navController)
        }

        composable(Rotas.REGISTRAR) {
            TelaRegistrar(navController)
        }

        composable(Rotas.FEED) {
            TelaFeed(navController)
        }

        composable(Rotas.PERFIL) {
            TelaPerfil(navController)
        }

        composable(Rotas.NOVA_PUBLI) {
            TelaNovaPubli(navController)
        }
    }
}