package com.example.tmdb.features.SecondFragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
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
    var isClickListenerActivated = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(arguments.movieDetails) {
            viewModel.checkIfMovieInWishlist(id)
            binding.apply {
                tvMovieName.text = title
                tvMovieOverview.text = overview

                val requestOptions = RequestOptions()
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)

                Glide.with(ivMovie.context)
                    .applyDefaultRequestOptions(requestOptions)
                    .load("https://image.tmdb.org/t/p/original${posterPath}")
                    .into(ivMovie)

                Glide.with(ivVerticalImage.context)
                    .applyDefaultRequestOptions(requestOptions)
                    .load("https://image.tmdb.org/t/p/original${posterPath}")
                    .into(ivVerticalImage)

                viewModel.isMovieWishlisted.observe(viewLifecycleOwner) { isWishlisted ->
                    if (isWishlisted && isClickListenerActivated) {
                        viewModel.deleteFromFavourites(arguments.movieDetails)
                        ivWishlist.setImageResource(R.drawable.ic_wishlist_empty)
                    } else if (!isWishlisted && isClickListenerActivated) {
                        viewModel.updateFavourites(arguments.movieDetails)
                        ivWishlist.setImageResource(R.drawable.ic_wishlist_filled)
                    } else if (isWishlisted) {
                        ivWishlist.setImageResource(R.drawable.ic_wishlist_filled)
                    } else {
                        ivWishlist.setImageResource(R.drawable.ic_wishlist_empty)
                    }
                    isClickListenerActivated = false
                }

                ivWishlist.setOnClickListener {
                    viewModel.checkIfMovieInWishlist(id)
                    isClickListenerActivated = true
                }

                btnTrailer.setOnClickListener {
                    Intent(
                        Intent.ACTION_SEARCH
                    ).also {
                        it.`package` = "com.google.android.youtube"
                        it.putExtra("query", "${tvMovieName.text} trailer")
                        try {
                            startActivity(it)
                        } catch (e: ActivityNotFoundException) {
                            e.printStackTrace()
                        }
                    }
                }

                btnShare.setOnClickListener {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, "${tvMovieName.text}")
                    }
                    if (context?.packageManager?.let { it1 -> intent.resolveActivity(it1) } != null) {
                        startActivity(intent)
                    }
                }
            }
        }
    }
}