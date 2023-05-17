package com.example.fakestoreapp.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "User", indices = arrayOf(Index(value = ["mail"], unique = true)))
data class User(
    @PrimaryKey(autoGenerate = true) var userID :Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "mail") val email: String,
    @ColumnInfo(name = "password") val password: String
)