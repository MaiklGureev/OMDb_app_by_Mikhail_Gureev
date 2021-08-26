package com.gureev.omdbapp.repository.room.favorite

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MoviesFavorite")
data class MovieShortRoomEntity(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "imdbID") val imdbID: String,
    @ColumnInfo(name = "Title") val title: String,
    @ColumnInfo(name = "Year") val year: String,
    @ColumnInfo(name = "Type") val type: String,
    @ColumnInfo(name = "Poster") val poster: String,
)