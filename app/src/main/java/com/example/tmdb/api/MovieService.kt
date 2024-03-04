package com.example.tmdb.api

import com.example.tmdb.models.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("3/movie/popular?api_key=$apiKey")
    suspend fun getPopularMovies(@Query("page") page: Int): Response<MoviesResponse>

    @GET("3/movie/top_rated?api_key=$apiKey")
    suspend fun getTopRatedMovies(@Query("page") page: Int): Response<MoviesResponse>
}

const val apiKey = "e96bf69e308c87d0ca9c132726381186"