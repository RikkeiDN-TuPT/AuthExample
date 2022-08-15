package com.example.test_app.data.models

data class ApiMovieDBResult(
    val page: Int,
    val results: List<MoviesDetails>,
    val total_pages: Int,
    val total_results: Int
)