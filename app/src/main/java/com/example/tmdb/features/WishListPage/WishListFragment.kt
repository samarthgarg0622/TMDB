package com.example.tmdb.features.WishListPage

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tmdb.R
import com.example.tmdb.adapter.MovieListingAdapter
import com.example.tmdb.baseComponent.BaseFragment
import com.example.tmdb.databinding.FragmentWishListBinding
import com.example.tmdb.features.FirstFragment.MoviesListingFragmentDirections
import com.example.tmdb.models.ResultsItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WishListFragment : BaseFragment<FragmentWishListBinding>(FragmentWishListBinding::inflate) {

    private val viewModel: WishListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.actionBar?.title = "Favourites"
        val movieListAdapter by lazy {
            MovieListingAdapter{ _ -> }
        }
        viewModel.getWishListMovies()

        viewModel.wishListMoviesLiveData.observe(viewLifecycleOwner) {
            movieListAdapter.modifyDataSetChanged(it)
        }

        binding.rvWishList.apply {
            adapter = movieListAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
    }
}