package com.example.mb_programming_work.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mb_programming_work.data.repository.MovieRepository
import com.example.mb_programming_work.ui.screens.favorites.FavoritesEvent
import com.example.mb_programming_work.ui.screens.favorites.FavoritesState
import com.example.mb_programming_work.ui.screens.home.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoritesViewModel : ViewModel() {

    private val repository = MovieRepository()

    private val _state = MutableStateFlow(FavoritesState())
    val state: StateFlow<FavoritesState> = _state.asStateFlow()

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
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            repository.observeFavorites().collect { favoriteList ->
                _state.update {
                    it.copy(
                        favoriteMovies = favoriteList,
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun removeFromFavorites(movie: Movie) {
        viewModelScope.launch {
            repository.toggleFavorite(movie, isCurrentlyFavorite = true)
        }
    }
}