package com.gureev.omdbapp.repository.retrofit.response

import com.google.gson.annotations.SerializedName

data class RatingsResponse(
    @SerializedName("Source") val source: String,
    @SerializedName("Value") val value: String
)