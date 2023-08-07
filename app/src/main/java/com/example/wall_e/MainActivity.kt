package com.example.wall_e

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.wall_e.home.HomeScreen
import com.example.wall_e.ui.theme.WallETheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WallETheme(darkTheme = true) {
                HomeScreen()
            }
        }
    }
}
