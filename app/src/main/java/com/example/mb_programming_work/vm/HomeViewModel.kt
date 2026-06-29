package com.example.mb_programming_work.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mb_programming_work.data.repository.MovieRepository
import com.example.mb_programming_work.ui.screens.home.HomeEvent
import com.example.mb_programming_work.ui.screens.home.HomeState
import com.example.mb_programming_work.ui.screens.home.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val repository = MovieRepository()

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

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

    private fun toggleFavourite(movie: Movie) {
        val isFavourite = _state.value.favouriteMovies.contains(movie)
        viewModelScope.launch {
            repository.toggleFavorite(movie, isCurrentlyFavorite = isFavourite)
        }
    }

    private fun observeFirebaseFavorites() {
        viewModelScope.launch {
            repository.observeFavorites().collect { favoriteList ->
                _state.update { it.copy(favouriteMovies = favoriteList) }
            }
        }
    }

    private fun loadMovies() {
        viewModelScope.launch {
            val moviesList = repository.getAllMovies()
            _state.update { it.copy(movies = moviesList) }
        }
    }
}