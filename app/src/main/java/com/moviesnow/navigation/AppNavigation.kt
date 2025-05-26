package com.moviesnow.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.moviesnow.features.movies.ui.MovieListScreen
import com.moviesnow.features.movies.ui.MovieDetailScreen
import com.moviesnow.features.movies.viewmodel.MovieViewModel

@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    val vm: MovieViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home") {
            MovieListScreen(navController = navController,  vm = vm)
        }

        composable("details/{movieId}") { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId") ?: ""
            MovieDetailScreen(movieId = movieId, navController = navController, vm = vm)
        }
    }
}