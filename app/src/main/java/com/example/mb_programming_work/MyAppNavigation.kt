package com.example.mb_programming_work

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mb_programming_work.ui.routes.LoginRoute
import com.example.mb_programming_work.ui.routes.MenuRoute
import com.example.mb_programming_work.ui.routes.RegisterRoute
import com.example.mb_programming_work.ui.screens.login.LoginScreen
import com.example.mb_programming_work.ui.screens.menu.MenuScreen
import com.example.mb_programming_work.ui.screens.register.RegisterScreen

@Composable
fun MyAppNavigation(modifier: Modifier = Modifier) {
    //kontroleri ekranebs shoris gadasvlis samartavad
    val navController = rememberNavController()
    //grapis gansazghvra da sawyisi ekranis mititeba
    NavHost(
        navController = navController,
        startDestination = MenuRoute
    ) {
        //konkretuli marshrutis dakavshireba shesabamis ekrantan
        composable<MenuRoute> {
            MenuScreen(
                modifier = modifier,
                onLoginClick = {
                    navController.navigate(LoginRoute) {
                        launchSingleTop = true
                        popUpTo(MenuRoute) { saveState = false }
                    }
                },
                onRegisterClick = {
                    navController.navigate(RegisterRoute) {
                        launchSingleTop = true
                        popUpTo(MenuRoute) { saveState = false }
                    }
                }
            )
        }

        composable<LoginRoute> {
            LoginScreen(
                modifier = modifier,
                onLoginClick = {

                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable<RegisterRoute> {
            RegisterScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onRegisterClick = {

                },
                modifier = modifier
            )
        }
    }
}