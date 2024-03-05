package com.example.tmdb.api

import com.example.findingfalcone.util.BaseDataSource
import com.example.findingfalcone.util.Resource
import com.example.tmdb.models.MoviesResponse
import javax.inject.Inject

class DataResource @Inject constructor(
    private val movieService: MovieService
) : BaseDataSource() {
    suspend fun getMovies(page: Int, category: String): Resource<MoviesResponse> {
        if (category == "popular")
            return getResult { movieService.getPopularMovies(page) }
        return getResult { movieService.getTopRatedMovies(page) }
    }
}