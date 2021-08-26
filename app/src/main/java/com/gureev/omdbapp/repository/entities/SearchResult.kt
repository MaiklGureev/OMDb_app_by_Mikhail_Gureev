package com.gureev.omdbapp.repository.entities

data class SearchResult(
    val movieShortResponseList: List<MovieShort>,
    val totalResults: Int,
    val response: Boolean,
    val error: String,
)