package com.gureev.omdbapp.repository.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gureev.omdbapp.repository.room.cache.MovieFullCachedRoomEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface MovieCachedRoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg movieFullCachedRoomEntity: MovieFullCachedRoomEntity): Completable

    @Query("SELECT * FROM MoviesCached WHERE imdbID = :id")
    fun getById(id: String): Single<MovieFullCachedRoomEntity>

    @Query("delete from MoviesCached where imdbID =:id")
    fun deleteById(id: String): Completable

    @Query("delete from MoviesCached")
    fun deleteAll(): Completable

}