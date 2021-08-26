package com.gureev.omdbapp.repository.source

import com.gureev.omdbapp.repository.entities.MovieFull
import com.gureev.omdbapp.repository.entities.MovieShort
import com.gureev.omdbapp.repository.room.MovieDatabase
import com.gureev.omdbapp.repository.room.MovieMapperForRoom
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class DiskDataSource @Inject constructor(
    private val db: MovieDatabase,
    private val mapper: MovieMapperForRoom
) {


    //favorite
    val listOfFavoriteMovies: Observable<List<MovieShort>> = db.moviesFavoritesRoomDao().getAll()
        .map { list ->
            mapper.fromRoomDatabaseToMovieShortList(list)
        }

    fun getFavoriteMovieShort(imdbID: String): Single<MovieShort> =
        db.moviesFavoritesRoomDao().getById(imdbID)
            .map { mapper.fromMovieShortRoomEntityToMovieShort(it) }

    fun insertMovieToFavorite(movie: MovieShort): Completable =
        db.moviesFavoritesRoomDao().insertMovie(mapper.fromMovieFullToMovieShortRoomEntity(movie))

    fun insertMovieToFavorite(movie: MovieFull): Completable =
        db.moviesFavoritesRoomDao().insertMovie(mapper.fromMovieFullToMovieShortRoomEntity(movie))

    fun insertMoviesToFavorite(movieList: List<MovieShort>): Completable =
        db.moviesFavoritesRoomDao().insertMovie(
            *mapper.fromMovieShortListToMovieShortRoomEntityList(movieList).toTypedArray()
        )

    fun removeFavoriteMovie(imdbID: String): Completable =
        db.moviesFavoritesRoomDao().deleteById(imdbID)

    fun removeAllFavoriteMovies(): Completable =
        db.moviesFavoritesRoomDao().deleteAll()

    //cached
    fun insertMovieFullToCache(movie: MovieFull): Completable =
        db.moviesCachedRoomDao().insert(mapper.fromMovieFullToMovieFullCachedRoomEntity(movie))

    fun removeAllCachedMovies(): Completable =
        db.moviesCachedRoomDao().deleteAll()

    fun getMovieFullFromCache(imdbID: String): Single<MovieFull> =
        db.moviesCachedRoomDao().getById(imdbID)
            .map { mapper.fromCachedRoomDatabaseToMovieFull(it) }
}