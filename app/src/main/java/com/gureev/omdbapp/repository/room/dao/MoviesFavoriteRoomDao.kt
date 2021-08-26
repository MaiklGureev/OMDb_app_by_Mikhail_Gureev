package com.gureev.omdbapp.repository.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gureev.omdbapp.repository.room.favorite.MovieShortRoomEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface MoviesFavoriteRoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(vararg movieShortRoomEntity: MovieShortRoomEntity): Completable

    @Query("select * from MoviesFavorite ORDER BY Title ASC")
    fun getAll(): Observable<List<MovieShortRoomEntity>>

    @Query("SELECT * FROM MoviesFavorite WHERE imdbID = :id")
    fun getById(id: String): Single<MovieShortRoomEntity>

    @Query("delete from MoviesFavorite where imdbID =:id")
    fun deleteById(id: String): Completable

    @Query("delete from MoviesFavorite")
    fun deleteAll(): Completable

}