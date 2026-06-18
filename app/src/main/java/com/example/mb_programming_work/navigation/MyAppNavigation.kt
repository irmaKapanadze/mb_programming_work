package com.example.mb_programming_work.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.mb_programming_work.navigation.routes.HomeRoute
import com.example.mb_programming_work.navigation.routes.LoginRoute
import com.example.mb_programming_work.navigation.routes.MainGraph
import com.example.mb_programming_work.navigation.routes.MenuRoute
import com.example.mb_programming_work.navigation.routes.ProfileRoute
import com.example.mb_programming_work.navigation.routes.RegisterRoute
import com.example.mb_programming_work.ui.screens.home.HomeScreen
import com.example.mb_programming_work.ui.screens.home.ProfileScreen
import com.example.mb_programming_work.ui.screens.login.LoginScreen
import com.example.mb_programming_work.ui.screens.menu.MenuScreen
import com.example.mb_programming_work.ui.screens.register.RegisterScreen
import com.example.mb_programming_work.ui.theme.MyTheme
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MyAppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val isUserLoggedIn = FirebaseAuth.getInstance().currentUser != null
    val startDestination = if (isUserLoggedIn) MainGraph else MenuRoute

    val showBottomBar = navBackStackEntry?.destination?.hierarchy?.any {
        it.route == MainGraph::class.qualifiedName
    } == true

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomBar(navController)
            }
        },
        containerColor = MyTheme.colors.background,
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = modifier.padding(paddingValues)
        ) {
            composable<MenuRoute> {
                MenuScreen(
                    modifier = modifier,
                    onLoginClick = { navController.navigate(LoginRoute) { launchSingleTop = true } },
                    onRegisterClick = { navController.navigate(RegisterRoute) { launchSingleTop = true } }
                )
            }

            composable<LoginRoute> {
                LoginScreen(
                    modifier = modifier,
                    onLoginClick = {
                        navController.navigate(MainGraph) {
                            popUpTo(MenuRoute) { inclusive = true }
                        }
                    },
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable<RegisterRoute> {
                RegisterScreen(
                    modifier = modifier,
                    onBackClick = { navController.popBackStack() },
                    onRegisterClick = {
                        navController.navigate(MainGraph) {
                            popUpTo(MenuRoute) { inclusive = true }
                        }
                    }
                )
            }

            navigation<MainGraph>(startDestination = HomeRoute) {
                homeNavGraph(navController)
                profileNavGraph(navController)
            }
        }
    }
}

fun NavGraphBuilder.homeNavGraph(navController: NavHostController) {
    composable<HomeRoute> {
        HomeScreen(

        )
    }
}

fun NavGraphBuilder.profileNavGraph(navController: NavHostController) {
    composable<ProfileRoute> {
        ProfileScreen(

        )
    }
}