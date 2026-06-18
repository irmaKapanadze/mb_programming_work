package com.example.mb_programming_work.ui.screens.favorites

import com.example.mb_programming_work.ui.screens.home.model.MovieUi

sealed interface FavoritesEvent {
    data class OnUnfavouriteClick(val movie: MovieUi) : FavoritesEvent
}