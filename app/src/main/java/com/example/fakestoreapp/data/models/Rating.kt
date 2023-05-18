package com.example.fakestoreapp.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

/*data class Rating(
    val count: Int,
    val rate: Double
)*/
@Serializable
@Entity(tableName = "Rating")
data class Rating(
    @PrimaryKey var id :Int?,
    @ColumnInfo(name = "count") var count :Int,
    @ColumnInfo(name = "rate") val rate: Double,
)