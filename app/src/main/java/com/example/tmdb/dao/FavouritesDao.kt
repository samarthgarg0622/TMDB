package com.example.tmdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tmdb.models.ResultsItem

@Dao
interface FavouritesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavourite(movie: ResultsItem)

    @Query("DELETE FROM favourites WHERE id=:id")
    suspend fun removeFavourite(id: Int)

    @Query("SELECT * FROM favourites")
    suspend fun getFavourites() : List<ResultsItem>

    @Query("SELECT COUNT(*) FROM favourites WHERE id = :id")
    suspend fun checkIfMovieInWishlist(id: Int): Int
}