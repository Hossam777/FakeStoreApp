package com.example.fakestoreapp.data.repositories

import com.example.fakestoreapp.data.local.UserDao
import com.example.fakestoreapp.data.models.User
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UserRepository @Inject constructor(private val userDao: UserDao) {
    suspend fun checkIfEmailIsFound(mail: String): Boolean {
        var isFound = false
        userDao.findByMail(mail)?.let { isFound = true }
        return isFound
    }

    suspend fun login(mail: String, password: String): Single<User> {
        return userDao.findByMailAndPassword(mail, password)
    }

    suspend fun signup(user: User) {
        userDao.addUser(user)
    }
}