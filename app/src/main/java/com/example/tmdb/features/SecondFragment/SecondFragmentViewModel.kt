package com.example.tmdb.features.SecondFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.Repository.MoviesRepository
import com.example.tmdb.models.ResultsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecondFragmentViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
): ViewModel() {
    var isMovieWishlisted = MutableLiveData<Boolean>()
    fun updateFavourites(movie: ResultsItem) {
        viewModelScope.launch {
            moviesRepository.addFavourites(movie)
        }
    }

    fun checkIfMovieInWishlist(id: Int) =
        viewModelScope.launch {
            isMovieWishlisted.postValue(moviesRepository.checkIfMovieInWishlist(id) > 0)

    }

    fun deleteFromFavourites(movie: ResultsItem) {
        viewModelScope.launch {
            moviesRepository.deleteFromFavourites(movie)
        }
    }
}