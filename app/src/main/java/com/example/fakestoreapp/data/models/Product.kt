package com.example.fakestoreapp.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/*data class Product(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val rating: Rating,
    val title: String
)*/

@Entity(tableName = "SavedProducts")
data class Product(
    @PrimaryKey var productId :Int?,
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "price") val price: Double,
    @ColumnInfo(name = "rating") val rating: Rating,
    @ColumnInfo(name = "title") val title: String,
)