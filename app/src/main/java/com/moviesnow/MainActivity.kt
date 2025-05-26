package com.moviesnow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.moviesnow.navigation.AppNavigation
import com.moviesnow.ui.theme.MoviesNowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesNowTheme {
                val navController = rememberNavController()
                AppNavigation(navController = navController)

            }
        }
    }
}
