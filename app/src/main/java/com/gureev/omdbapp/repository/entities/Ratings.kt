package com.gureev.omdbapp.repository.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ratings(
    val source: String,
    val value: String
) : Parcelable
