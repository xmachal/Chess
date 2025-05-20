package com.application.chess.data

import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun fromSuccessPaths(value: List<List<Pair<Int, Int>>>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toSuccessPaths(value: String): List<List<Pair<Int, Int>>> {
        val type = object : TypeToken<List<List<Pair<Int, Int>>>>() {}.type
        return Gson().fromJson(value, type)
    }
}