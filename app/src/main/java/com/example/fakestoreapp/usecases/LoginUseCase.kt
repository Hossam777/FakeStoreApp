package com.example.fakestoreapp.usecases

import com.example.fakestoreapp.data.models.User
import com.example.fakestoreapp.data.repositories.UserRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend fun login(email: String, password: String): Single<User> {
       return userRepository.login(email, password)
    }
}