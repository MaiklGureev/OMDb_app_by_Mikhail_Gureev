package com.gureev.omdbapp.repository.retrofit.response

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("Search") val shortSearches: List<MovieShortResponse>?,
    @SerializedName("totalResults") val totalResults: Int?,
    @SerializedName("Response") val response: Boolean,
    @SerializedName("Error") val error: String?,
)