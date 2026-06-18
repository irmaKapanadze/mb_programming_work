package com.example.mb_programming_work.ui.screens.favorites

import com.example.mb_programming_work.ui.screens.home.model.MovieUi

data class FavoritesState(
    val isLoading: Boolean = false,
    val favoriteMovies: List<MovieUi> = emptyList()
)