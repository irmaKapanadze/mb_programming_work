package com.example.mb_programming_work.data.repository

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class RegisterRepository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    suspend fun registerUser(email: String, password: String): Result<Unit> {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}