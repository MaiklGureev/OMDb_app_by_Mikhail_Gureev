package com.gureev.omdbapp.repository.room.cache

import androidx.room.ColumnInfo

//@Entity(tableName = "RatingsRoomEntity")
class RatingsRoomEntity(
//     @ColumnInfo(name = "Source") val source : String,
//     @ColumnInfo(name = "Value") val value : String,
    @ColumnInfo(name = "Source") val source: String,
    @ColumnInfo(name = "Value") val value: String,
)