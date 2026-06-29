package com.example.mb_programming_work.data.repository

import com.example.mb_programming_work.ui.screens.home.model.Movie
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class MovieRepository {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val userId: String? get() = auth.currentUser?.uid

    suspend fun getAllMovies(): List<Movie> {
        return try {
            val snapshot = db.collection("movies").get().await()
            snapshot.documents.mapNotNull { document ->
                val data = document.data ?: return@mapNotNull null
                mapDocumentToMovie(document.id, data)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    fun observeFavorites(): Flow<List<Movie>> = callbackFlow {
        val id = userId
        if (id == null) {
            trySend(emptyList())
            close()
            return@callbackFlow
        }

        val listener = db.collection("users")
            .document(id)
            .collection("favorites")
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) return@addSnapshotListener

                val favoriteList = snapshot.documents.mapNotNull { document ->
                    val data = document.data ?: return@mapNotNull null
                    mapDocumentToMovie(document.id, data)
                }
                trySend(favoriteList)
            }

        awaitClose { listener.remove() }
    }

    suspend fun toggleFavorite(movie: Movie, isCurrentlyFavorite: Boolean) {
        val id = userId ?: return
        val docRef = db.collection("users")
            .document(id)
            .collection("favorites")
            .document(movie.id)

        try {
            if (isCurrentlyFavorite) {
                docRef.delete().await()
            } else {
                docRef.set(movie).await()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun mapDocumentToMovie(id: String, data: Map<String, Any>): Movie {
        return Movie(
            id = id,
            title = data["title"] as? String ?: "",
            description = data["description"] as? String ?: "",
            imageUrl = data["imageUrl"] as? String ?: "",
            rating = (data["rating"] as? Long)?.toInt() ?: 0,
            releaseYear = (data["releaseYear"] as? Long)?.toInt() ?: 0,
            genre = data["genre"] as? String ?: "",
            duration = data["duration"] as? String ?: "",
            cast = (data["cast"] as? List<*>)?.mapNotNull { it as? String } ?: emptyList(),
            videoUrl = data["videoUrl"] as? String ?: ""
        )
    }
}