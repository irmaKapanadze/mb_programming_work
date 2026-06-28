package com.example.mb_programming_work.ui.screens.favorites

import com.example.mb_programming_work.ui.screens.home.model.Movie

sealed interface FavoritesEvent {
    data class OnUnfavouriteClick(val movie: Movie) : FavoritesEvent
}