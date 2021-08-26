package com.gureev.omdbapp.repository.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieFull(
    val imdbID: String = "",
    val title: String = "",
    val year: String = "",
    val rated: String = "",
    val released: String = "",
    val runtime: String = "",
    val genre: String = "",
    val director: String = "",
    val writer: String = "",
    val actors: String = "",
    val plot: String = "",
    val language: String = "",
    val country: String = "",
    val awards: String = "",
    val poster: String = "",
    val ratings: List<Ratings>? = emptyList(),
    val metascore: String = "",
    val imdbRating: Double = 0.0,
    val imdbVotes: String = "",
    val type: String = "",
    val totalSeasons: Int = 0,
    val response: Boolean = false,
) : Parcelable