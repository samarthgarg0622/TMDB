package com.example.tmdb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tmdb.R
import com.example.tmdb.models.ResultsItem

class MovieListingAdapter(
    val onPress: (ResultsItem) -> Unit
) : RecyclerView.Adapter<MovieListingAdapter.MovieItemViewHolder>() {
    private var moviesList: List<ResultsItem> = listOf()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item_layout, parent, false)
        val itemView = MovieItemViewHolder(view)
        return itemView
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        when (holder) {
            is MovieItemViewHolder -> {
                holder.bind(moviesList[position], onPress)
            }
        }
    }

    fun modifyDataSetChanged(listOfMovies: List<ResultsItem>) {
        moviesList = listOfMovies
        notifyDataSetChanged()
    }

    class MovieItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(movieDetails: ResultsItem, onPress: (ResultsItem) -> Unit) {
            itemView.findViewById<TextView>(R.id.tv_movie_name).text = movieDetails.originalTitle
            itemView.findViewById<TextView>(R.id.tv_movie_description).text = movieDetails.overview
            val imageView = itemView.findViewById<ImageView>(R.id.iv_movie_image)
            val cardView = itemView.findViewById<CardView>(R.id.cv_layout)
            val requestOptions = RequestOptions()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load("https://image.tmdb.org/t/p/original${movieDetails.posterPath}")
                .into(imageView)

            cardView.setOnClickListener {
                onPress(movieDetails)
            }
        }

    }
}