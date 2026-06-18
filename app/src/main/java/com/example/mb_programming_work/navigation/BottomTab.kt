package com.example.mb_programming_work.navigation

data class BottomTab<T : Any>(
    val route: T,
    val iconRes: Int
)