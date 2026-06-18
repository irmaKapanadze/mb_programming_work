package com.example.mb_programming_work.vm

import androidx.lifecycle.ViewModel
import com.example.mb_programming_work.ui.screens.favorites.FavoritesEvent
import com.example.mb_programming_work.ui.screens.favorites.FavoritesState
import com.example.mb_programming_work.ui.screens.home.model.MovieUi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FavoritesViewModel : ViewModel() {

    private val _state = MutableStateFlow(FavoritesState())
    val state: StateFlow<FavoritesState> = _state.asStateFlow()

    private val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()

    init {
        observeFavorites()
    }

    fun onEvent(event: FavoritesEvent) {
        when (event) {
            is FavoritesEvent.OnUnfavouriteClick -> {
                removeFromFavorites(event.movie)
            }
        }
    }

    private fun observeFavorites() {
        val userId = auth.currentUser?.uid ?: return

        _state.update { it.copy(isLoading = true) }

        db.collection("users")
            .document(userId)
            .collection("favorites")
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) {
                    _state.update { it.copy(isLoading = false) }
                    return@addSnapshotListener
                }

                val favoriteList = snapshot.documents.mapNotNull { document ->
                    val data = document.data ?: return@mapNotNull null
                    MovieUi(
                        id = document.id,
                        title = data["title"] as? String ?: "",
                        description = data["description"] as? String ?: "",
                        imageUrl = data["imageUrl"] as? String ?: "",
                        rating = (data["rating"] as? Long)?.toInt() ?: 0,
                        releaseYear = (data["releaseYear"] as? Long)?.toInt() ?: 0,
                        genre = data["genre"] as? String ?: "",
                        duration = data["duration"] as? String ?: "",
                        cast = (data["cast"] as? List<*>)?.mapNotNull { it as? String }
                            ?: emptyList(),
                        videoUrl = data["videoUrl"] as? String ?: ""
                    )
                }

                _state.update {
                    it.copy(
                        favoriteMovies = favoriteList,
                        isLoading = false
                    )
                }
            }
    }

    private fun removeFromFavorites(movie: MovieUi) {
        val userId = auth.currentUser?.uid ?: return

        db.collection("users")
            .document(userId)
            .collection("favorites")
            .document(movie.id)
            .delete()
    }
}