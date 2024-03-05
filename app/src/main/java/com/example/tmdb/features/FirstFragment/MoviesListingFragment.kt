package com.example.tmdb.features.FirstFragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findingfalcone.util.Resource
import com.example.tmdb.adapter.MovieListingAdapter
import com.example.tmdb.baseComponent.BaseFragment
import com.example.tmdb.databinding.FragmentMoviesListingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesListingFragment :
    BaseFragment<FragmentMoviesListingBinding>(FragmentMoviesListingBinding::inflate) {

    private val viewModel by viewModels<MoviesListingViewModel>()
    private val movieListAdapter by lazy {
        MovieListingAdapter { it2 ->
            val action =
                MoviesListingFragmentDirections.actionFirstFragmentToSecondFragment(it2)
            findNavController().navigate(action)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apply {
            moviesResponse.observe(viewLifecycleOwner) {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        it.data?.results?.let { listOfMovies ->
                            updateMoviesList(listOfMovies)
                            moviesListSize = listOfMovies.size
                        }
                    }

                    Resource.Status.ERROR -> {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
//                it.body()?.results?.let { it1 ->
//                    updateMoviesList(it1)
//                    moviesListSize = it1.size
//                }
            }

            binding.moviesList.apply {
                adapter = movieListAdapter
                layoutManager = GridLayoutManager(context, 1)
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        with(viewModel) {
                            if ((this@apply.layoutManager as? LinearLayoutManager)?.findLastCompletelyVisibleItemPosition()
                                    ?.plus(3) ==
                                moviesListSize
                            ) {
                                getMovies(viewModel.category)
                            }

                        }
                    }
                })
            }
            moviesList.observe(viewLifecycleOwner) {
                movieListAdapter.modifyDataSetChanged(it)
            }
            binding.apply {
                category1.setOnClickListener {
                    category = category1.text.toString()
                    getMovies(category)
                    category2.setOnClickListener {
                        category = category2.text.toString()
                        getMovies(category)
                    }

                }
            }
        }

    }
}