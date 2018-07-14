package ru.nickmiller.magpie.data.cache.dao

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*


class Converters {


    @TypeConverter
    fun fromString(value: String): ArrayList<String> {
        val listType = object : TypeToken<ArrayList<String>>() {}.type
        return Gson().fromJson<ArrayList<String>>(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<String>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromDate(value: Date): Long = value.time


    @TypeConverter
    fun fromDateTime(time: Long): Date = Date(time)
}