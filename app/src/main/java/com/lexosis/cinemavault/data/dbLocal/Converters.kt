package com.lexosis.cinemavault.data.dbLocal

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromGenresLocalList(genres: List<GenresLocal>): String {
        val gson = Gson()
        val type = object : TypeToken<List<GenresLocal>>() {}.type
        return gson.toJson(genres, type)
    }

    @TypeConverter
    fun toGenresLocalList(genresString: String): List<GenresLocal> {
        val gson = Gson()
        val type = object : TypeToken<List<GenresLocal>>() {}.type
        return gson.fromJson(genresString, type)
    }
}
