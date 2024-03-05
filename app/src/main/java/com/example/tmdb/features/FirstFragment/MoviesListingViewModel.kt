package com.example.tmdb.features.FirstFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findingfalcone.util.Resource
import com.example.tmdb.Repository.MoviesRepository
import com.example.tmdb.models.MoviesResponse
import com.example.tmdb.models.ResultsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MoviesListingViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {
    val moviesResponse = MutableLiveData<Resource<MoviesResponse>>()
    var moviesList = MutableLiveData<List<ResultsItem>>()
    var moviesListSize = 0
    var pageCount = 1
    var category = "popular"
    var prevCategory = ""
    fun getMovies(category: String) {
        viewModelScope.launch {
            if (moviesListSize == 0 || category != prevCategory) {
                moviesRepository.getMovies(1, category).collectLatest {
                    moviesResponse.postValue(it)
                }
            } else {
                moviesRepository.getMovies(pageCount++, category).collectLatest {
                    moviesResponse.postValue(it)
                }
            }
        }
    }

    fun updateMoviesList(list: List<ResultsItem>) {
        val newList = mutableListOf<ResultsItem>()
        if (moviesList.value?.isNotEmpty() == true) {
            moviesList.value?.let {
                if (category == prevCategory)
                    newList.addAll(it)
                else {
                    prevCategory = category
                }
            }
        }
        newList.addAll(list)
        moviesList.postValue(newList)
    }

    init {
        getMovies(category);
    }
}