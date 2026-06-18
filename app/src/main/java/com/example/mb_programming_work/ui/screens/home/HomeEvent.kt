package com.example.mb_programming_work.ui.screens.home

import com.example.mb_programming_work.ui.screens.home.model.MovieUi

sealed interface HomeEvent {
    data class OnSearchQueryChange(val query: String) : HomeEvent
    data class OnCategoryClick(val category: MovieGenre) : HomeEvent
    data class OnMovieClick(val movie: MovieUi?) : HomeEvent
    data class OnFavouriteClick(val movie: MovieUi) : HomeEvent
    object OnClearSearch : HomeEvent
}