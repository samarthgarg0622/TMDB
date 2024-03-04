package com.example.tmdb.features.WishListPage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.Repository.MoviesRepository
import com.example.tmdb.models.ResultsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishListViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    var wishListMoviesLiveData = MutableLiveData<List<ResultsItem>>()

    fun getWishListMovies() {
        viewModelScope.launch {
            wishListMoviesLiveData.postValue(moviesRepository.getWishListMovies())
        }
    }
}