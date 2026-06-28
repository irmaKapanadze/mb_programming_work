package com.example.mb_programming_work

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.mb_programming_work.navigation.MyAppNavigation
import com.example.mb_programming_work.ui.theme.MyTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyTheme {
                MyAppNavigation()
            }
        }
    }
}