package com.example.mb_programming_work.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mb_programming_work.ui.screens.home.HomeEvent
import com.example.mb_programming_work.ui.screens.home.HomeState
import com.example.mb_programming_work.ui.screens.home.model.MovieUi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class HomeViewModel : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()

    init {
        loadMovies()
        observeFirebaseFavorites()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnSearchQueryChange -> {
                _state.update {
                    it.copy(
                        searchQuery = event.query,
                        isSearchActive = event.query.isNotEmpty()
                    )
                }
            }

            HomeEvent.OnClearSearch -> {
                _state.update {
                    it.copy(
                        searchQuery = "",
                        isSearchActive = false
                    )
                }
            }

            is HomeEvent.OnCategoryClick -> {
                _state.update { it.copy(selectedCategory = event.category) }
            }

            is HomeEvent.OnMovieClick -> {
                _state.update { it.copy(selectedMovie = event.movie) }
            }

            is HomeEvent.OnFavouriteClick -> {
                toggleFavourite(event.movie)
            }
        }
    }

    private fun toggleFavourite(movie: MovieUi) {
        val userId = auth.currentUser?.uid ?: return

        val docRef = db.collection("users")
            .document(userId)
            .collection("favorites")
            .document(movie.id)

        val isFavourite = _state.value.favouriteMovies.contains(movie)

        if (isFavourite) {
            docRef.delete()
        } else {
            docRef.set(movie)
        }
    }

    private fun observeFirebaseFavorites() {
        val userId = auth.currentUser?.uid ?: return

        db.collection("users")
            .document(userId)
            .collection("favorites")
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) return@addSnapshotListener

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
                        cast = (data["cast"] as? List<*>)?.mapNotNull { it as? String } ?: emptyList(),
                        videoUrl = data["videoUrl"] as? String ?: ""
                    )
                }

                _state.update { it.copy(favouriteMovies = favoriteList) }
            }
    }

    private fun loadMovies() {
        viewModelScope.launch {
            try {
                val snapshot = db.collection("movies").get().await()

                val moviesList = snapshot.documents.mapNotNull { document ->
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
                        cast = (data["cast"] as? List<*>)?.mapNotNull { it as? String } ?: emptyList(),
                        videoUrl = data["videoUrl"] as? String ?: ""
                    )
                }

                _state.update { it.copy(movies = moviesList) }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}