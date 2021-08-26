package com.gureev.omdbapp.repository.retrofit.response

import com.google.gson.annotations.SerializedName

data class MovieShortResponse(
    @SerializedName("imdbID") val imdbID: String,
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("Type") val type: String,
    @SerializedName("Poster") val poster: String
)
