package com.gureev.omdbapp.repository.room

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.gureev.omdbapp.repository.room.cache.RatingsRoomEntity
import java.lang.reflect.Type

@ProvidedTypeConverter
class TypeConverterForRoom {

    private var gson = Gson()

    @TypeConverter
    fun stringToSomeObjectList(data: String?): List<RatingsRoomEntity?>? {
        if (data == null) {
            return emptyList<RatingsRoomEntity>()
        }
        val listType: Type = object : TypeToken<List<RatingsRoomEntity?>?>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<RatingsRoomEntity?>?): String? {
        return gson.toJson(someObjects)
    }

}