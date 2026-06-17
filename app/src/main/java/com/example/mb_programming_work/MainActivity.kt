package com.example.mb_programming_work

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.mb_programming_work.ui.theme.MyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            //aplikacias moergeba 'MyAppTheme'
            MyTheme {
                //sawiroa sistemuri dashorebebis avtomaturi martvistvis
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MyTheme.colors.background
                ) { innerPadding ->
                    //navigaciis ierarqia
                    MyAppNavigation(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}