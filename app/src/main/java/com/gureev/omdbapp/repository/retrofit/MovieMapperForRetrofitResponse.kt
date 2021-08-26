package com.gureev.omdbapp.repository.retrofit

import com.gureev.omdbapp.repository.entities.MovieFull
import com.gureev.omdbapp.repository.entities.MovieShort
import com.gureev.omdbapp.repository.entities.Ratings
import com.gureev.omdbapp.repository.entities.SearchResult
import com.gureev.omdbapp.repository.retrofit.response.MovieFullResponse
import com.gureev.omdbapp.repository.retrofit.response.MovieShortResponse
import com.gureev.omdbapp.repository.retrofit.response.SearchResponse

class MovieMapperForRetrofitResponse {

    fun fromMovieShortResponseListToMovieShortList(list: List<MovieShortResponse>?): List<MovieShort> {
        return list?.map {
            MovieShort(
                imdbID = it.imdbID,
                title = it.title,
                year = it.year,
                poster = it.poster,
                type = it.type
            )
        } ?: emptyList()
    }

    fun fromRetrofitResponseToMovieShort(movie: MovieShortResponse): MovieShort {
        return MovieShort(
            imdbID = movie.imdbID,
            title = movie.title,
            year = movie.year,
            poster = movie.poster,
            type = movie.type,
        )
    }

    fun fromRetrofitResponseToMovie(movie: MovieFullResponse): MovieFull {
        return MovieFull(
            imdbID = movie?.imdbID ?: "",
            title = movie?.title ?: "",
            year = movie?.year ?: "",
            rated = movie?.rated ?: "",
            released = movie?.released ?: "",
            runtime = movie?.runtime ?: "",
            genre = movie?.genre ?: "",
            director = movie?.director ?: "",
            writer = movie?.writer ?: "",
            actors = movie?.actors ?: "",
            plot = movie?.plot ?: "",
            language = movie?.language ?: "",
            country = movie?.country ?: "",
            awards = movie?.awards ?: "",
            poster = movie?.poster ?: "",
            ratings = movie?.ratings?.map { Ratings(it.source, it.value) } ?: emptyList(),
            metascore = movie?.metascore ?: "",
            imdbRating = movie?.imdbRating ?: 0.0,
            imdbVotes = movie?.imdbVotes ?: "",
            type = movie?.type ?: "",
            totalSeasons = movie?.totalSeasons ?: 0,
            response = movie?.response ?: false,
        )
    }

    fun fromSearchResponseToSearchResult(searchResponse: SearchResponse): SearchResult {
        return SearchResult(
            movieShortResponseList = fromMovieShortResponseListToMovieShortList(searchResponse.shortSearches),
            totalResults = searchResponse.totalResults ?: 0,
            response = searchResponse.response,
            error = searchResponse.error ?: "Without error.",
        )
    }
}