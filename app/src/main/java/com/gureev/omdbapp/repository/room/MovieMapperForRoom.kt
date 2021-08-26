package com.gureev.omdbapp.repository.room

import com.gureev.omdbapp.repository.entities.MovieFull
import com.gureev.omdbapp.repository.entities.MovieShort
import com.gureev.omdbapp.repository.entities.Ratings
import com.gureev.omdbapp.repository.room.cache.MovieFullCachedRoomEntity
import com.gureev.omdbapp.repository.room.cache.RatingsRoomEntity
import com.gureev.omdbapp.repository.room.favorite.MovieShortRoomEntity

class MovieMapperForRoom {
    fun fromCachedRoomDatabaseToMovieFull(movie: MovieFullCachedRoomEntity): MovieFull {
        return MovieFull(
            imdbID = movie.imdbID,
            title = movie.title,
            year = movie.year,
            rated = movie.rated,
            released = movie.released,
            runtime = movie.runtime,
            genre = movie.genre,
            director = movie.director,
            writer = movie.writer,
            actors = movie.actors,
            plot = movie.plot,
            language = movie.language,
            country = movie.country,
            awards = movie.awards,
            poster = movie.poster,
            ratings = movie.ratings.map { Ratings(it?.source.toString(), it?.value.toString()) },
            metascore = movie.metascore,
            imdbRating = movie.imdbRating,
            imdbVotes = movie.imdbVotes,
            type = movie.type,
            totalSeasons = movie.totalSeasons,
            response = movie.response,
        )
    }

    fun fromMovieFullCachedRoomEntityListToMovieFullList(list: List<MovieFullCachedRoomEntity>): List<MovieFull> {
        return list.map {
            MovieFull(
                imdbID = it.imdbID,
                title = it.title,
                year = it.year,
                rated = it.rated,
                released = it.released,
                runtime = it.runtime,
                genre = it.genre,
                director = it.director,
                writer = it.writer,
                actors = it.actors,
                plot = it.plot,
                language = it.language,
                country = it.country,
                awards = it.awards,
                poster = it.poster,
                ratings = it.ratings.map { Ratings(it?.source.toString(), it?.value.toString()) },
                metascore = it.metascore,
                imdbRating = it.imdbRating,
                imdbVotes = it.imdbVotes,
                type = it.type,
                totalSeasons = it.totalSeasons,
                response = it.response,
            )
        }
    }

    fun fromMovieFullToMovieShortRoomEntity(movie: MovieShort): MovieShortRoomEntity {
        return MovieShortRoomEntity(
            imdbID = movie.imdbID,
            title = movie.title,
            year = movie.year,
            poster = movie.poster,
            type = movie.type,
        )
    }

    fun fromRoomDatabaseToMovieShortList(list: List<MovieShortRoomEntity>): List<MovieShort> {
        return list.map {
            MovieShort(
                imdbID = it.imdbID,
                title = it.title,
                year = it.year,
                poster = it.poster,
                type = it.type,
            )
        }
    }

    fun fromMovieShortListToMovieShortRoomEntityList(movieList: List<MovieShort>): List<MovieShortRoomEntity> {
        return movieList.map { movie ->
            MovieShortRoomEntity(
                imdbID = movie.imdbID,
                title = movie.title,
                year = movie.year,
                poster = movie.poster,
                type = movie.type,
            )
        }
    }

    fun fromMovieFullToMovieShortRoomEntity(movie: MovieFull): MovieShortRoomEntity {
        return MovieShortRoomEntity(
            imdbID = movie.imdbID,
            title = movie.title,
            year = movie.year,
            poster = movie.poster,
            type = movie.type,
        )
    }

    fun fromMovieFullToMovieFullCachedRoomEntity(movie: MovieFull): MovieFullCachedRoomEntity {
        return MovieFullCachedRoomEntity(
            imdbID = movie.imdbID,
            title = movie.title,
            year = movie.year,
            rated = movie.rated,
            released = movie.released,
            runtime = movie.runtime,
            genre = movie.genre,
            director = movie.director,
            writer = movie.writer,
            actors = movie.actors,
            plot = movie.plot,
            language = movie.language,
            country = movie.country,
            awards = movie.awards,
            poster = movie.poster,
            ratings = movie.ratings?.map { RatingsRoomEntity(it.source, it.value) } ?: emptyList(),
            metascore = movie.metascore,
            imdbRating = movie.imdbRating,
            imdbVotes = movie.imdbVotes,
            type = movie.type,
            totalSeasons = movie.totalSeasons,
            response = movie.response,
        )
    }

    fun fromMovieShortRoomEntityToMovieShort(movie: MovieShortRoomEntity): MovieShort {
        return MovieShort(
            imdbID = movie.imdbID,
            title = movie.title,
            year = movie.year,
            poster = movie.poster,
            type = movie.type,
        )
    }
}