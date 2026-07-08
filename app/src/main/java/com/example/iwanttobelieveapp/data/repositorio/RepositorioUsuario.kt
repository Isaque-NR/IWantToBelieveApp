package com.example.iwanttobelieveapp.data.repositorio

import com.example.iwanttobelieveapp.data.model.UsuarioApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class RepositorioUsuario {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun buscarUsuarioLogado(): Result<UsuarioApp> {
        return try {
            val usuarioFirebase = auth.currentUser
                ?: throw Exception("Esse usuário não está logado.")

            val uid = usuarioFirebase.uid

            val documento = firestore
                .collection("users")
                .document(uid)
                .get()
                .await()

            val usuario = documento.toObject(UsuarioApp::class.java)
                ?: throw Exception("Perfil do usuário não encontrado.")

            Result.success(usuario)

        } catch (erro: Exception) {
            Result.failure(erro)
        }
    }
}