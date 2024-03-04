package com.example.tmdb.models

data class MoviesResponse(
    val page: Int = 0,
    val totalPages: Int = 0,
    val results: List<ResultsItem>?,
    val totalResults: Int = 0
)