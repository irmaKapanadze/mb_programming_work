package com.example.mb_programming_work

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.mb_programming_work.data.datastore.DataStoreManager
import com.example.mb_programming_work.navigation.MyAppNavigation
import com.example.mb_programming_work.ui.theme.MyTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val dataStoreManager = DataStoreManager(applicationContext)
        setContent {
            val isDarkThemeSaved by dataStoreManager.isDarkThemeFlow.collectAsState(initial = null)
            val isDarkTheme = isDarkThemeSaved ?: isSystemInDarkTheme()
            MyTheme(darkTheme = isDarkTheme) {
                MyAppNavigation()
            }
        }
    }
}