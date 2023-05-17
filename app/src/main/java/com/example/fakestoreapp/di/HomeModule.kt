package com.example.fakestoreapp.di

import com.example.fakestoreapp.data.remote.NetworkManager
import com.example.fakestoreapp.data.repositories.ProductsRepository
import com.example.fakestoreapp.ui.home.HomeViewModel
import com.example.fakestoreapp.usecases.GetAllProductsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object HomeModule {
    @Provides
    fun provideProductsRepository(networkManager: NetworkManager): ProductsRepository
    = ProductsRepository(networkManager)

    @Provides
    fun provideGetAllProductsUseCase(productsRepository: ProductsRepository): GetAllProductsUseCase
    = GetAllProductsUseCase(productsRepository)

    fun provideHomeViewModel(getAllProductsUseCase: GetAllProductsUseCase): HomeViewModel = HomeViewModel(getAllProductsUseCase)
}