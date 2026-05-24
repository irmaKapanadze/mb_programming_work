package com.example.mb_programming_work

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mb_programming_work.ui.routes.StudentFormRoute
import com.example.mb_programming_work.ui.screens.StudentForm

@Composable
fun MyAppNavigation(modifier: Modifier = Modifier) {
    //kontroleri ekranebs shoris gadasvlis samartavad
    val navController = rememberNavController()
    //grapis gansazghvra da sawyisi ekranis mititeba
    NavHost(
        navController = navController,
        startDestination = StudentFormRoute
    ) {
        //konkretuli marshrutis dakavshireba shesabamis ekrantan
        composable<StudentFormRoute> {
            StudentForm(modifier)
        }
    }
}