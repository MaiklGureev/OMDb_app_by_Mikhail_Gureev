package com.gureev.omdbapp.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gureev.omdbapp.repository.room.cache.MovieFullCachedRoomEntity
import com.gureev.omdbapp.repository.room.dao.MovieCachedRoomDao
import com.gureev.omdbapp.repository.room.dao.MoviesFavoriteRoomDao
import com.gureev.omdbapp.repository.room.favorite.MovieShortRoomEntity

@Database(
    entities = [MovieFullCachedRoomEntity::class, MovieShortRoomEntity::class],
    version = 1
)
@TypeConverters(TypeConverterForRoom::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun moviesFavoritesRoomDao(): MoviesFavoriteRoomDao
    abstract fun moviesCachedRoomDao(): MovieCachedRoomDao
}