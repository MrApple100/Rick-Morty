package ru.mrapple100.core.character.datasource.local.entity

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListConverter{
    @TypeConverter
    fun convertListToJSONString(invoiceList: List<String>): String = Gson().toJson(invoiceList)
    @TypeConverter
    fun convertJSONStringToList(jsonString: String): List<String> = Gson().fromJson(jsonString,
        object : TypeToken<List<String>>() {}.type)

}