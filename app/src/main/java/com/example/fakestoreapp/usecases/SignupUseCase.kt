package com.example.fakestoreapp.usecases

import com.example.fakestoreapp.data.models.User
import com.example.fakestoreapp.data.repositories.UserRepository
import javax.inject.Inject

class SignupUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend fun signup(name: String, mail: String, password: String): Boolean {
        return if(!userRepository.checkIfEmailIsFound(mail)){
            userRepository.signup(User(name=name, email=mail, password=password))
            true
        }else{
            false
        }
    }
}