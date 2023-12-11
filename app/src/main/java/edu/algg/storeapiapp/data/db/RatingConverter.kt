package edu.algg.storeapiapp.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import edu.algg.storeapiapp.data.api.Rating

class RatingConverter {
    @TypeConverter
    fun fromRecursosList(rate: Rating): String = Gson().toJson(rate)

    @TypeConverter
    fun toRecursosList(rate: Rating): Rating {
        val type = object : TypeToken<Rating>() {}.type
        //return Gson().fromJson(rate, type)
        return rate
    }
}