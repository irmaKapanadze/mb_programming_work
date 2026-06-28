package com.example.mb_programming_work.ui.screens.home.model

data class Movie(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val rating: Int = 0,
    val releaseYear: Int = 0,
    val genre: String = "",
    val duration: String = "",
    val cast: List<String> = emptyList(),
    val videoUrl: String = "",
)