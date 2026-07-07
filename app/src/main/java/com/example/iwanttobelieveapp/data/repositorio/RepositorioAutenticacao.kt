package com.example.iwanttobelieveapp.data.repositorio

import com.example.iwanttobelieveapp.data.model.UsuarioApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class RepositorioAutenticacao {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun cadastrarUsuario(
        nome: String,
        email: String,
        senha: String
    ): Result<Unit> {
        return try {
            val resultadoAuth =
                auth.createUserWithEmailAndPassword(email, senha).await()

            val usuarioFirebase = resultadoAuth.user
                ?: throw Exception("Erro ao obter usuário criado.")

            val uid = usuarioFirebase.uid

            val usuario = UsuarioApp(
                uid = uid,
                nome = nome,
                email = email
            )

            firestore
                .collection("users")
                .document(uid)
                .set(usuario)
                .await()

            Result.success(Unit)

        } catch (erro: Exception) {
            Result.failure(erro)
        }
    }
    suspend fun loginUsuario(
        email: String,
        senha: String
    ): Result<Unit> {
        return try {
            auth.signInWithEmailAndPassword(email, senha).await()

            Result.success(Unit)

        } catch (erro: Exception) {
            Result.failure(erro)
        }
    }

    fun usuarioLogado() = auth.currentUser

    fun sair() {
        auth.signOut()
    }


}