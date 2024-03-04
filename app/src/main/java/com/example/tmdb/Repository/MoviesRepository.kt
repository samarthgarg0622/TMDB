package com.example.tmdb.Repository

import com.example.tmdb.api.MovieService
import com.example.tmdb.dao.FavouritesDao
import com.example.tmdb.models.MoviesResponse
import com.example.tmdb.models.ResultsItem
import retrofit2.Response
import javax.inject.Inject


class MoviesRepository @Inject constructor(
    private val movieService: MovieService,
    private val favouritesDao: FavouritesDao
) {
    suspend fun getMovies(page: Int, category: String): Response<MoviesResponse> {
        if (category == "popular")
            return movieService.getPopularMovies(page)
        return movieService.getTopRatedMovies(page)
    }

    suspend fun addFavourites(movie: ResultsItem) {
        favouritesDao.addFavourite(movie)
    }

    suspend fun getWishListMovies() =
        favouritesDao.getFavourites()

    suspend fun checkIfMovieInWishlist(id: Int) = favouritesDao.checkIfMovieInWishlist(id)

    suspend fun deleteFromFavourites(movie: ResultsItem) {
        favouritesDao.removeFavourite(movie.id)
    }
}