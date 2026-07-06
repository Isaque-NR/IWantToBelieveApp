package com.example.iwanttobelieveapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.iwanttobelieveapp.ui.navegacao.NavegacaoApp
import com.example.iwanttobelieveapp.ui.theme.IWantToBelieveAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            IWantToBelieveAppTheme {
                NavegacaoApp()
            }
        }
    }
}