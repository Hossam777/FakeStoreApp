package com.example.fakestoreapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fakestoreapp.data.models.User

@Database(entities = [User::class], version = 1)
abstract class FakeStoreDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}