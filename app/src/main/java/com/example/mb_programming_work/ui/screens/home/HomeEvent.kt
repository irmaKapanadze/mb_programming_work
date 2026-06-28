package com.example.mb_programming_work.ui.screens.home

import com.example.mb_programming_work.ui.screens.home.model.Movie

sealed interface HomeEvent {
    data class OnSearchQueryChange(val query: String) : HomeEvent
    data class OnCategoryClick(val category: MovieGenre) : HomeEvent
    data class OnMovieClick(val movie: Movie?) : HomeEvent
    data class OnFavouriteClick(val movie: Movie) : HomeEvent
    object OnClearSearch : HomeEvent
}