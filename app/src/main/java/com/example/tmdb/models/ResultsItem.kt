package com.example.tmdb.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Favourites")
data class ResultsItem(
    val overview: String = "",
    @SerializedName("original_language")
    val originalLanguage: String = "",
    @SerializedName("original_title")
    val originalTitle: String = "",
    val video: Boolean = false,
    val title: String = "",
//    @SerializedName("genre_ids")
//    val genreIds: List<Integer>?,
    @SerializedName("poster_path")
    val posterPath: String = "",
    @SerializedName("backdrop_path")
    val backdropPath: String = "",
    @SerializedName("release_date")
    val releaseDate: String = "",
    val popularity: Double = 0.0,
    @SerializedName("vote_average")
    val voteAverage: Double = 0.0,
    @PrimaryKey
    val id: Int = 0,
    val adult: Boolean = false,
    @SerializedName("vote_count")
    val voteCount: Int = 0
) : Parcelable