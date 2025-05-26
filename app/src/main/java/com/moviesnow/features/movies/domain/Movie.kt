package com.moviesnow.features.movies.domain

data class Movie(
    val id: String,
    val title: String,
    val description: String,
    val releaseYear: Int,
    val durationMinutes: Int,
    val rating: Double,
    val posterUrl: String,
    val trailerUrl: String
)