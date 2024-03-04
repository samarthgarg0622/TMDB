package com.example.tmdb.features.SecondFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tmdb.R
import com.example.tmdb.baseComponent.BaseFragment
import com.example.tmdb.databinding.FragmentSecondBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class SecondFragment : BaseFragment<FragmentSecondBinding>(FragmentSecondBinding::inflate) {

    private val arguments by navArgs<SecondFragmentArgs>()
    private val viewModel by viewModels<SecondFragmentViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(arguments.movieDetails) {
            viewModel.checkIfMovieInWishlist(id)
            binding.apply {
                tvMovieName.text = title
                tvMovieRating.text = voteAverage.toString()

                val requestOptions = RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)

                Glide.with(ivMovie.context)
                    .applyDefaultRequestOptions(requestOptions)
                    .load("https://image.tmdb.org/t/p/original${posterPath}")
                    .into(ivMovie)

                addRemoveFavourites.setOnClickListener {
                    viewModel.updateFavourites(arguments.movieDetails)
                }

                viewModel.isMovieWishlisted.observe(viewLifecycleOwner) { isWishlisted ->
                    if (isWishlisted) {
                        ivWishlist.setImageResource(R.drawable.ic_wishlist_filled)
                    } else {
                        ivWishlist.setImageResource(R.drawable.ic_wishlist_empty)
                    }

                    ivWishlist.setOnClickListener {
                        if (isWishlisted) {
                            viewModel.deleteFromFavourites(arguments.movieDetails)
                            ivWishlist.setImageResource(R.drawable.ic_wishlist_empty)
                        } else {
                            viewModel.updateFavourites(arguments.movieDetails)
                            ivWishlist.setImageResource(R.drawable.ic_wishlist_filled)
                        }
                    }
                }
            }
        }
    }
}