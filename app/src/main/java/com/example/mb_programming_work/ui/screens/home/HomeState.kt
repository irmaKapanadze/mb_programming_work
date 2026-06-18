package com.example.mb_programming_work.ui.screens.home

import com.example.mb_programming_work.ui.screens.home.model.MovieUi

data class HomeState(
    val movies: List<MovieUi> = emptyList(),
    val favouriteMovies: List<MovieUi> = emptyList(),
    val selectedCategory: MovieGenre = MovieGenre.ALL,
    val searchQuery: String = "",
    val selectedMovie: MovieUi? = null,
    val isSearchActive: Boolean = false
)