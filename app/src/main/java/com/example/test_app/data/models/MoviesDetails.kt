package com.example.test_app.data.models

data class MoviesDetails(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val poster_path: String,
    val original_language: String,
    val title: String,
    val vote_average: Float,
    val video: Boolean
)