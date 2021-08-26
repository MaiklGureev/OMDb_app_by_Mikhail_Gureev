package com.gureev.omdbapp.repository.room.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.gureev.omdbapp.repository.room.TypeConverterForRoom

@Entity(tableName = "MoviesCached")
data class MovieFullCachedRoomEntity(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "imdbID") val imdbID: String,
    @ColumnInfo(name = "Title") val title: String,
    @ColumnInfo(name = "Year") val year: String,
    @ColumnInfo(name = "Rated") val rated: String,
    @ColumnInfo(name = "Released") val released: String,
    @ColumnInfo(name = "Runtime") val runtime: String,
    @ColumnInfo(name = "Genre") val genre: String,
    @ColumnInfo(name = "Director") val director: String,
    @ColumnInfo(name = "Writer") val writer: String,
    @ColumnInfo(name = "Actors") val actors: String,
    @ColumnInfo(name = "Plot") val plot: String,
    @ColumnInfo(name = "Language") val language: String,
    @ColumnInfo(name = "Country") val country: String,
    @ColumnInfo(name = "Awards") val awards: String,
    @ColumnInfo(name = "Poster") val poster: String,
    @ColumnInfo(name = "RatingsResponse") @TypeConverters(TypeConverterForRoom::class) val ratings: List<RatingsRoomEntity?>,
    @ColumnInfo(name = "Metascore") val metascore: String,
    @ColumnInfo(name = "imdbRating") val imdbRating: Double,
    @ColumnInfo(name = "imdbVotes") val imdbVotes: String,
    @ColumnInfo(name = "Type") val type: String,
    @ColumnInfo(name = "totalSeasons") val totalSeasons: Int,
    @ColumnInfo(name = "Response") val response: Boolean,
)