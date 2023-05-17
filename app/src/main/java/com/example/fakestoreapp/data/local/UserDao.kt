package com.example.fakestoreapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.fakestoreapp.data.models.User
import io.reactivex.rxjava3.core.Single

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE mail == :mail LIMIT 1")
    suspend fun findByMail(mail: String): User?

    @Query("SELECT * FROM user")
    fun getAllUsers(): List<User>

    @Query("SELECT * FROM user WHERE mail == :mail AND password == :password LIMIT 1")
    fun findByMailAndPassword(mail: String, password: String): Single<User>

    @Insert
    suspend fun addUser(user: User)

    @Delete
    suspend fun delete(user: User)
}