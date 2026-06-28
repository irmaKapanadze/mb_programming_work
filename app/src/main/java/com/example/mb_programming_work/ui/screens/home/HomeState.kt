package com.example.mb_programming_work.ui.screens.home

import com.example.mb_programming_work.ui.screens.home.model.Movie

data class HomeState(
    val movies: List<Movie> = emptyList(),
    val favouriteMovies: List<Movie> = emptyList(),
    val selectedCategory: MovieGenre = MovieGenre.ALL,
    val searchQuery: String = "",
    val selectedMovie: Movie? = null,
    val isSearchActive: Boolean = false,
)