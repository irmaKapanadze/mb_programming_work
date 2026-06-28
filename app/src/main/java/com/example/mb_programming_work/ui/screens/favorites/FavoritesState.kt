package com.example.mb_programming_work.ui.screens.favorites

import com.example.mb_programming_work.ui.screens.home.model.Movie

data class FavoritesState(
    val isLoading: Boolean = false,
    val favoriteMovies: List<Movie> = emptyList(),
)