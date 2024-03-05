package com.example.tmdb.Repository

import com.example.findingfalcone.util.Resource
import com.example.tmdb.api.DataResource
import com.example.tmdb.dao.FavouritesDao
import com.example.tmdb.models.MoviesResponse
import com.example.tmdb.models.ResultsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class MoviesRepository @Inject constructor(
    private val dataResource: DataResource,
    private val favouritesDao: FavouritesDao
) {
    suspend fun getMovies(page: Int, category: String): Flow<Resource<MoviesResponse>> = flow {
        emit(dataResource.getMovies(page, category))
    }.flowOn(Dispatchers.IO)

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