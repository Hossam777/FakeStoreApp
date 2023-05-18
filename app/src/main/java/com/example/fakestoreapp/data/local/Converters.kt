package com.example.fakestoreapp.data.local

import androidx.room.TypeConverter
import com.example.fakestoreapp.data.models.Rating
import com.example.fakestoreapp.utils.JsonConverter
import kotlinx.serialization.*
import kotlinx.serialization.json.*

class Converters {
    @TypeConverter
    fun fromRatingToString(rating: Rating?): String {
        return Json.encodeToString(rating)
    }

    @TypeConverter
    fun fromStringToRating(string: String?): Rating? {
        return string?.let { JsonConverter.convertJsonToObj(it, Rating::class.java) }
    }
}