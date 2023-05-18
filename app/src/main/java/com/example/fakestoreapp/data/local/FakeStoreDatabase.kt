package com.example.fakestoreapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fakestoreapp.data.models.Product
import com.example.fakestoreapp.data.models.Rating
import com.example.fakestoreapp.data.models.User

@Database(entities = [User::class, Product::class, Rating::class], version = 1)
@TypeConverters(Converters::class)
abstract class FakeStoreDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun savedProductsDao(): SavedProductsDao
}