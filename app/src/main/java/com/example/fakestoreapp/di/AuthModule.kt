package com.example.fakestoreapp.di

import android.app.Application
import android.content.Context
import com.example.fakestoreapp.data.local.FakeStoreDatabase
import com.example.fakestoreapp.data.local.UserDao
import com.example.fakestoreapp.data.repositories.UserRepository
import com.example.fakestoreapp.ui.authentication.AuthViewModel
import com.example.fakestoreapp.usecases.LoginUseCase
import com.example.fakestoreapp.usecases.SignupUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import javax.inject.Singleton

@Module
@InstallIn(FragmentComponent::class)
object AuthModule {

    @Provides
    fun provideUserRepository(userDao: UserDao): UserRepository = UserRepository(userDao)

    @Provides
    fun provideLoginUseCase(userRepository: UserRepository): LoginUseCase = LoginUseCase(userRepository)

    @Provides
    fun provideSignupUseCase(userRepository: UserRepository): SignupUseCase = SignupUseCase(userRepository)

    @Provides
    fun provideAuthViewModel(loginUseCase: LoginUseCase, signupUseCase: SignupUseCase): AuthViewModel =
        AuthViewModel(loginUseCase, signupUseCase)
}