package com.example.fakestoreapp.di

import com.example.fakestoreapp.data.remote.NetworkManager
import com.example.fakestoreapp.data.repositories.ProductsRepository
import com.example.fakestoreapp.ui.home.HomeViewModel
import com.example.fakestoreapp.usecases.GetAllCategoriesUseCase
import com.example.fakestoreapp.usecases.GetProductsUseCase
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
    fun provideGetProductsUseCase(productsRepository: ProductsRepository): GetProductsUseCase
    = GetProductsUseCase(productsRepository)

    @Provides
    fun provideGetAllCategoriesUseCase(productsRepository: ProductsRepository): GetAllCategoriesUseCase
            = GetAllCategoriesUseCase(productsRepository)
    fun provideHomeViewModel(getProductsUseCase: GetProductsUseCase
                             , getAllCategoriesUseCase: GetAllCategoriesUseCase): HomeViewModel
    = HomeViewModel(getProductsUseCase, getAllCategoriesUseCase)
}