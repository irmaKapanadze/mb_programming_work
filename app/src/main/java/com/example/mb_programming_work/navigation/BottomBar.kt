package com.example.mb_programming_work.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.wear.compose.material.Icon
import androidx.wear.compose.navigation.currentBackStackEntryAsState
import com.example.mb_programming_work.R
import com.example.mb_programming_work.navigation.routes.HomeRoute
import com.example.mb_programming_work.navigation.routes.ProfileRoute
import com.example.mb_programming_work.ui.theme.MyTheme

@Composable
fun BottomBar(navController: NavController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    val tabs = listOf(
        BottomTab(HomeRoute, R.drawable.home_svgrepo_com),
        BottomTab(ProfileRoute, R.drawable.profile_svgrepo_com)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .background(MyTheme.colors.surface)
    ) {
        NavigationBar(
            containerColor = MyTheme.colors.primaryContainer,
            tonalElevation = 0.dp
        ) {
            tabs.forEach { tab ->
                val isSelected = currentDestination?.hierarchy?.any {
                    it.route == tab.route::class.qualifiedName
                } == true

                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        navController.navigate(tab.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(tab.iconRes),
                            contentDescription = null,
                            tint = if (isSelected) MyTheme.colors.accent else MyTheme.colors.textSecondary
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MyTheme.colors.accent,
                        unselectedIconColor = MyTheme.colors.textSecondary,
                        indicatorColor = MyTheme.colors.surface
                    )
                )
            }
        }
    }
}