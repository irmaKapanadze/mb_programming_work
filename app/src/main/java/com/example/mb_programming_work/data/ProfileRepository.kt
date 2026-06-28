package com.example.mb_programming_work.data

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class ProfileRepository {
    private val auth = FirebaseAuth.getInstance()

    fun getCurrentUserEmail(): String {
        return auth.currentUser?.email ?: "guest@user.com"
    }

    fun logOut() {
        auth.signOut()
    }

    suspend fun deleteUserAccount(password: String): Result<Unit> {
        val user = auth.currentUser ?: return Result.failure(Exception("User not logged in"))
        val email = user.email ?: return Result.failure(Exception("Email not found"))

        return try {
            val credential = EmailAuthProvider.getCredential(email, password)

            user.reauthenticate(credential).await()

            user.delete().await()

            auth.signOut()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}