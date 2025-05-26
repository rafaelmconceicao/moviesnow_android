package com.moviesnow.features.movies.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.moviesnow.features.movies.domain.Movie
import com.moviesnow.features.movies.data.FakeMovieService

class MovieViewModel(app: Application) : AndroidViewModel(app) {
    private val service = FakeMovieService(app)

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    fun loadMovies() {
        _movies.value = service.getMovies()
    }
}